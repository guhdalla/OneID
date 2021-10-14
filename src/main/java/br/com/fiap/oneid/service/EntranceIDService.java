package br.com.fiap.oneid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntranceIDService {
	
	private UsuarioJuridicoService serviceJuridico;

	@Autowired
	public EntranceIDService(UsuarioJuridicoService serviceJuridico) {
		this.serviceJuridico = serviceJuridico;
	}
}
