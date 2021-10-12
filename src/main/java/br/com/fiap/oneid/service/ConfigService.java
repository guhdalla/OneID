package br.com.fiap.oneid.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioJuridico;

@Service
public class ConfigService {

	private UsuarioJuridicoService serviceJuridico;

	@Autowired
	public ConfigService(UsuarioJuridicoService serviceJuridico) {
		this.serviceJuridico = serviceJuridico;
	}
}
