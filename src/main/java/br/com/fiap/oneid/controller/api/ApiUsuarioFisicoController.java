package br.com.fiap.oneid.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class ApiUsuarioFisicoController {

	@Autowired
	private UsuarioRepository repository;
	
	
}		
