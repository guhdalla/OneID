package br.com.fiap.oneid.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import br.com.fiap.oneid.repository.UsuarioFisicoRepository;
import br.com.fiap.oneid.repository.UsuarioJuridicoRepository;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository repository;
	
	@Autowired
	static AtividadeRepository repositoryStatic;

	@Autowired
	static UsuarioFisicoRepository repositoryUsuarioFisico;

	@Autowired
	static UsuarioJuridicoRepository repositoryUsuarioJuridico;
	
	@Autowired
	static TagRepository repositoryTag;
	
	@Autowired
	static DispositivoRepository repositoryDispositivo;

	@Autowired
	private TokenService tokenService;

	public static Atividade create(MqttRequest request) {
		Atividade atividade = new Atividade();
//		SimpleDateFormat formatador = new SimpleDateFormat("");
		
		
		atividade.setDtCheck(new Date());
		
		Optional<Tag> tag = repositoryTag.findByCodigoTag(request.getIdTag());
		System.out.println(tag.get());
		if(tag.isEmpty()) return null;
		System.out.println(tag.get());
		
		Optional<Dispositivo> dispositivo = repositoryDispositivo.findByCdDispositivo(request.getIdDispositivo());
		if(dispositivo.isEmpty()) return null;
		
		atividade.setUsuarioFisico((UsuarioFisico) tag.get().getUsuario());
		atividade.setUsuarioJuridico(dispositivo.get().getUsuarioJuridico());
		
		int nrCheck = verificaCheck(atividade);
		atividade.setNrCheck(nrCheck);
		return repositoryStatic.save(atividade);
	}

	public static int verificaCheck(Atividade atividade) {
		List<Atividade> atividades = repositoryStatic.findByUsuarioJuridicoAndUsuarioFisico(
				atividade.getUsuarioJuridico(), 
				atividade.getUsuarioFisico(), 
				Sort.by("idAtividade")
				);
		System.out.println("Verifica nrCheck");
		System.out.println(atividades);
		return 0;
	}
	
	public Usuario getUsuarioByToken(HttpServletRequest request){
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

}
