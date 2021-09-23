package br.com.fiap.oneid.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.model.mqtt.MqttRequest;
import br.com.fiap.oneid.repository.AtividadeRepository;
import br.com.fiap.oneid.repository.UsuarioFisicoRepository;
import br.com.fiap.oneid.repository.UsuarioJuridicoRepository;

@Service
public class AtividadeService {
	
	@Autowired
	static AtividadeRepository repository;
	
	@Autowired
	static UsuarioFisicoRepository repositoryUsuarioFisico;
	
	@Autowired
	static UsuarioJuridicoRepository repositoryUsuarioJuridico;
	
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
}
