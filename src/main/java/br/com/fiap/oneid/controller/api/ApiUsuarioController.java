package br.com.fiap.oneid.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.oneid.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class ApiUsuarioController {

	@Autowired
	private UsuarioRepository repository;
}
