package br.com.fiap.oneid.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.Dispositivo;
import br.com.fiap.oneid.model.Tag;
import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.model.mqtt.MqttRequest;
import br.com.fiap.oneid.repository.AtividadeRepository;
import br.com.fiap.oneid.repository.DispositivoRepository;
import br.com.fiap.oneid.repository.TagRepository;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository repository;

	@Autowired
	private TagRepository repositoryTag;

	@Autowired
	private DispositivoRepository repositoryDispositivo;

	@Autowired
	private TokenService tokenService;

	public Atividade create(MqttRequest request) {
		Atividade atividade = new Atividade();

		atividade.setDtCheck(new Date());

		Optional<Tag> tag = repositoryTag.findByCodigoTag(request.getIdTag());
		if (tag.isEmpty()) {
			return null;
		}
		if (tag.get().getUsuario() == null || tag.get().getNumeroStatus() != 1) return null;

		Optional<Dispositivo> dispositivo = repositoryDispositivo.findByCdDispositivo(request.getIdDispositivo());
		if (dispositivo.isEmpty()) {
			return null;
		}
		if (dispositivo.get().getStatusDispositivo() != 1 || dispositivo.get().getUsuarioJuridico() == null) return null;
	
		
		if (tag.isPresent() && dispositivo.isPresent()) {
			atividade.setUsuarioFisico((UsuarioFisico) tag.get().getUsuario());
			atividade.setUsuarioJuridico(dispositivo.get().getUsuarioJuridico());
		
			int nrCheck = verificaCheck(atividade);
			atividade.setNrCheck(nrCheck);
			
			atividade.setIdAtividade(null);
			
			return repository.save(atividade);
		}
		return null;
	}

	public int verificaCheck(Atividade atividade) {
		List<Atividade> atividades = repository.findByUsuarioJuridicoAndUsuarioFisico(atividade.getUsuarioJuridico(),
				atividade.getUsuarioFisico(), Sort.by("idAtividade"));
		
		if(atividades.isEmpty()) return 1;
		
		if (atividades.get(atividades.size() - 1).getNrCheck() == 1 && atividade.getUsuarioJuridico().getIdUsuario() == atividades.get(atividades.size() - 1).getUsuarioJuridico().getIdUsuario()) {
			return 0;
		} else if (atividades.get(atividades.size() - 1).getNrCheck() == 1 && atividade.getUsuarioJuridico().getIdUsuario() != atividades.get(atividades.size() - 1).getUsuarioJuridico().getIdUsuario()) {
			return 1;
		}
		if (atividades.get(atividades.size() - 1).getNrCheck() == 0) {
			return 1;
		} 
		return 0;
	}

	public Usuario getUsuarioByToken(HttpServletRequest request) {
		return (Usuario) tokenService.findByToken(tokenService.extractToken(request));
	}

	public List<Atividade> getAllAtividade(HttpServletRequest request) {
		Usuario usuario = getUsuarioByToken(request);
		if (usuario.getRoles().get(0).getAuthority().equals("ROLE_JURIDICO")) {
			UsuarioJuridico usuarioJuridico = (UsuarioJuridico) usuario;
			return repository.findByUsuarioJuridico(usuarioJuridico);
		} else {
			UsuarioFisico usuarioFisico = (UsuarioFisico) usuario;
			return repository.findByUsuarioFisico(usuarioFisico);
		}
	}

	public List<Atividade> findByUsuarioJuridico(UsuarioJuridico usuario) {
		return repository.findByUsuarioJuridico(usuario);
	}

}
