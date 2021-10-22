package br.com.fiap.oneid.service;

import br.com.fiap.oneid.model.*;
import br.com.fiap.oneid.model.mqtt.InitializingOnDemandHolder;
import br.com.fiap.oneid.model.mqtt.MqttRequest;
import br.com.fiap.oneid.repository.TagRepository;
import br.com.fiap.oneid.repository.TransacaoRepository;
import br.com.fiap.oneid.repository.UsuarioFisicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

	final InitializingOnDemandHolder INSTANCE = InitializingOnDemandHolder.getInstance();

	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private TagRepository repositoryTag;

	@Autowired
	private DispositivoService dispositivoService;
	
	@Autowired
	private UsuarioFisicoRepository repositoryUsuarioFisico;
	
	@Autowired
	private CarteiraService serviceCarteira;

	public List<Transacao> getAll() {
		return transacaoRepository.findAll();
	}

	public Dispositivo create(TransacaoPendente transacaoPendente, HttpServletRequest request) {
		Usuario usuario = getUsuarioByToken(request);
		Dispositivo dispositivo = verifyDispositivo(usuario, transacaoPendente.getCodigoDispositivo());
		if (dispositivo == null)
			throw new RuntimeException("Dispositivo não encontrado");

		boolean exist = INSTANCE.verifyContextExist(transacaoPendente.getCodigoDispositivo());
		if (exist)
			throw new RuntimeException("Transacao pendente ja existe");
		INSTANCE.setContext(transacaoPendente);
		return dispositivo;
	}

	public Optional<TransacaoPendente> delete(HttpServletRequest request, String codigoDispositivo,
			InitializingOnDemandHolder INSTANCE) {
		Dispositivo dispositivo = verifyDispositivo(getUsuarioByToken(request), codigoDispositivo);
		if (dispositivo == null)
			throw new RuntimeException("Dispositivo não encontrado");
		return INSTANCE.getContext().stream().filter(x -> x.getCodigoDispositivo().equals(codigoDispositivo))
				.findFirst();
	}

	public Dispositivo verifyDispositivo(Usuario usuario, String codigoDispositivo) {
		return dispositivoService.findByEmpresa(usuario.getIdUsuario()).stream()
				.filter(x -> x.getCdDispositivo().equals(codigoDispositivo)).findFirst().orElse(null);
	}

	public Usuario getUsuarioByToken(HttpServletRequest request) {
		return (Usuario) tokenService.findByToken(tokenService.extractToken(request));
	}

	public List<Transacao> getAllTransacao(HttpServletRequest request) {
		Usuario usuario = getUsuarioByToken(request);
		if (usuario.getRoles().get(0).getAuthority().equals("ROLE_JURIDICO")) {
			UsuarioJuridico usuarioJuridico = (UsuarioJuridico) usuario;
			return transacaoRepository.findByUsuarioJuridico(usuarioJuridico);
		} else {
			UsuarioFisico usuarioFisico = (UsuarioFisico) usuario;
			return transacaoRepository.findByUsuarioFisico(usuarioFisico);
		}
	}

	public Transacao finalizar(MqttRequest request) {
		System.out.println(request);
		Optional<Dispositivo> dispositivo = dispositivoService.findByCdDispositivo(request.getIdDispositivo());
		if (dispositivo.isEmpty())
			return null;
		if (dispositivo.get().getUsuarioJuridico() == null)
			return null;
		if (dispositivo.get().getStatusDispositivo() != 1)
			return null;
		System.out.println(dispositivo.get());

		Optional<TransacaoPendente> transacaoPendente = INSTANCE.getContextDetail(dispositivo.get().getCdDispositivo());
		if (transacaoPendente.isEmpty())
			return null;
		System.out.println(transacaoPendente);

		Optional<Tag> tag = repositoryTag.findByCodigoTag(request.getIdTag());
		if (tag.isEmpty()) {
			System.out.println("tag is null");
			return null;
		}
		if (tag.get().getUsuario() == null)
			return null;
		if (tag.get().getNumeroStatus() != 1)
			return null;
		
		System.out.println(tag.get());
		
		Optional<UsuarioFisico> usuarioFisico = repositoryUsuarioFisico.findById(tag.get().getUsuario().getIdUsuario());
		if(usuarioFisico.isEmpty())
			return null;
		
		System.out.println(usuarioFisico.get());
		
		boolean transferir = transferirValor(usuarioFisico.get(), dispositivo.get().getUsuarioJuridico(), transacaoPendente.get().getValorTransacao());
		
		System.out.println(transferir);

		if (dispositivo.isPresent() && tag.isPresent() && transacaoPendente.isPresent() && transferir) {
			
			INSTANCE.removeContextDetail(transacaoPendente.get());
			
			Transacao transacao = new Transacao();
			transacao.setDataMovimentacao(new Date());
			transacao.setUsuarioFisico(usuarioFisico.get());
			transacao.setUsuarioJuridico(dispositivo.get().getUsuarioJuridico());
			transacao.setValorTransacao(transacaoPendente.get().getValorTransacao());
			return transacaoRepository.save(transacao);
		}
		
		return null;
	}

	private boolean transferirValor(UsuarioFisico usuarioFisico, UsuarioJuridico usuarioJuridico, float valor) {
		if (usuarioFisico.getCarteira().getSaldo() < valor) 
			return false;
		try {
			System.out.println("Antes" + usuarioFisico.getCarteira());
			Carteira carteira = serviceCarteira.alterarSaldo(usuarioFisico.getCarteira().getId(), valor * -1);
			System.out.println("Depois" + carteira);
			serviceCarteira.alterarSaldo(usuarioJuridico.getCarteira().getId(), valor);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
