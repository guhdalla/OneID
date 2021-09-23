package br.com.fiap.oneid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.fiap.oneid.service.AtividadeService;

@Controller
public class AtividadeController {
	
	final AtividadeService service;

	@Autowired
	public AtividadeController(AtividadeService service) {
		this.service = service;
	}
}
