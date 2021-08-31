package br.com.fiap.oneid.controller.api;

import java.net.URI;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import br.com.fiap.oneid.repository.UsuarioJuridicoRepository;

@RestController
@RequestMapping("/api/usuario")
@Api(tags="Endpoint Usuario Juridico")
public class ApiUsuarioJuridicoController {

	@Autowired
	private UsuarioJuridicoRepository repo;

	@ApiOperation(value = "Cadastrar Usuario Juridico")
	@PostMapping
	public ResponseEntity<UsuarioJuridico> cadastrarUsuario(@RequestBody UsuarioJuridico usuario, UriComponentsBuilder uriBuilder){
		repo.save(usuario);
		URI uri = uriBuilder.path("/api/usuario/{id}").buildAndExpand(usuario.getIdUsuario()).toUri();
		return ResponseEntity.created(uri).body(usuario);
	}

	@ApiOperation(value = "Atualizar Usuario Juridico")
	@PutMapping
	public ResponseEntity<UsuarioJuridico> atualizarDados(@PathVariable Long id, @RequestBody UsuarioJuridico usuario){
		return repo.findById(id).map(x -> {
			x.setEmail(usuario.getEmail());
			x.setFotoPerfil(usuario.getFotoPerfil());
			x.setSenha(usuario.getSenha());
			x.setEndereco(usuario.getEndereco());
			x.setTelefone(usuario.getTelefone());
			UsuarioJuridico userAtualizado = repo.save(x);
			return ResponseEntity.ok().body(userAtualizado);
		}).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Apagar Usuario Usuario Juridico por Id")
	@DeleteMapping
	public ResponseEntity<UsuarioJuridico> apagarConta(@PathVariable Long id){
		Optional<UsuarioJuridico> user = repo.findById(id);
		if (user.isEmpty()) return ResponseEntity.notFound().build();
		
		repo.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Buscar Usuario Juridico por Id")
	@GetMapping
	public ResponseEntity<UsuarioJuridico> consultarUsuario(@PathVariable Long id){
		Optional<UsuarioJuridico> user = repo.findById(id);
		if (!user.isEmpty()) 
			return ResponseEntity.ok(user.get());
		return ResponseEntity.notFound().build();

	}
	
}
	
