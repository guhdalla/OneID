package br.com.fiap.oneid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

	private UsuarioJuridicoService serviceJuridico;

	@Autowired
	public ConfigService(UsuarioJuridicoService serviceJuridico) {
		this.serviceJuridico = serviceJuridico;
	}
}
