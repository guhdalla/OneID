package br.com.fiap.oneid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioJuridico;

@Service
public class EntranceIDService {
	
	private UsuarioJuridicoService serviceJuridico;

	@Autowired
	public EntranceIDService(UsuarioJuridicoService serviceJuridico) {
		this.serviceJuridico = serviceJuridico;
	}
}
