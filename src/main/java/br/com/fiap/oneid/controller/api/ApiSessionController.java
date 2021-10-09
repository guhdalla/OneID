package br.com.fiap.oneid.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/log")
public class ApiSessionController {

	@Autowired
	private UsuarioRepository repository;
	
	@PostMapping("/in")
	public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
		Optional<Usuario> usuarioOp = repository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		if (usuarioOp.isPresent()) 
			return ResponseEntity.ok().body(usuarioOp.get());
		return ResponseEntity.notFound().build();
	}
	
}
