package br.com.fiap.oneid.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.Transacao;
import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.model.mqtt.MqttRequest;
import br.com.fiap.oneid.repository.AtividadeRepository;
import br.com.fiap.oneid.repository.UsuarioFisicoRepository;
import br.com.fiap.oneid.repository.UsuarioJuridicoRepository;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository repository;

	@Autowired
	static UsuarioFisicoRepository repositoryUsuarioFisico;

	@Autowired
	static UsuarioJuridicoRepository repositoryUsuarioJuridico;

	@Autowired
	private TokenService tokenService;

	public static Atividade create(MqttRequest request) {
		Atividade atividade = new Atividade();
		Calendar calendar = Calendar.getInstance();
		atividade.setDtCheck(calendar.getTime());
		atividade.setUsuarioFisico(null);
		atividade.setUsuarioJuridico(null);
		atividade.setNrCheck(0);
		return repository.save(atividade);
	}

	public int validaNrCheck(int num) {
		return 0;
	}
	
	public Usuario getUsuarioByToken(HttpServletRequest request){
        return (Usuario) tokenService.findByToken(tokenService.extractToken(request));
    }

	public List<Atividade> getAllTransacao(HttpServletRequest request) {
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
