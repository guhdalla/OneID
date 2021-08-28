package br.com.fiap.oneid.controller.api;

import java.net.URI; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.service.UsuarioJuridicoService;
 
@RestController
@RequestMapping("/api/usuario/juridico")
public class ApiUsuarioJuridicoController {

	private UsuarioJuridicoService repo;
	
	@Autowired
	public ApiUsuarioJuridicoController(UsuarioJuridicoService service) {
		this.repo = service;
	}
	
	@PostMapping
	public ResponseEntity<UsuarioJuridico> cadastrarUsuario(@RequestBody UsuarioJuridico usuario, UriComponentsBuilder uriBuilder){
		repo.create(usuario);
		URI uri = uriBuilder.path("/api/usuario/{id}").buildAndExpand(usuario.getIdUsuario()).toUri();
		return ResponseEntity.created(uri).body(usuario);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UsuarioJuridico> atualizarDados(@PathVariable Long id, @RequestBody UsuarioJuridico usuario){
		return repo.findById(id).map(x -> {
			x.setEmail(usuario.getEmail());
			x.setFotoPerfil(usuario.getFotoPerfil());
			x.setSenha(usuario.getSenha());
			x.setEndereco(usuario.getEndereco());
			x.setTelefone(usuario.getTelefone());
			UsuarioJuridico userAtualizado = repo.update(x.getIdUsuario(), usuario);
			return ResponseEntity.ok().body(userAtualizado);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<UsuarioJuridico> apagarConta(@PathVariable Long id){
		Optional<UsuarioJuridico> user = repo.findById(id);
		if (user.isEmpty()) return ResponseEntity.notFound().build();
		
		repo.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<UsuarioJuridico> consultarUsuario(@PathVariable Long id){
		Optional<UsuarioJuridico> user = repo.findById(id);
		if (!user.isEmpty()) 
			return ResponseEntity.ok(user.get());
		return ResponseEntity.notFound().build();

	}
	
}
	
