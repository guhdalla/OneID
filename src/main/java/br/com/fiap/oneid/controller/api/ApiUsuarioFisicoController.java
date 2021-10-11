package br.com.fiap.oneid.controller.api;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
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

import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.service.UsuarioFisicoService;

@RestController
@RequestMapping("/api/usuario/fisico")
public class ApiUsuarioFisicoController {

	final UsuarioFisicoService service;

	@Autowired
	public ApiUsuarioFisicoController(UsuarioFisicoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<UsuarioFisico> cadastrarUsuarioFisico(@RequestBody @Valid UsuarioFisico usuarioFisico,
			UriComponentsBuilder uriBuilder) {
		UsuarioFisico createdUsuarioFisico = service.create(usuarioFisico);
		if(createdUsuarioFisico == null) return ResponseEntity.badRequest().build();
		URI uri = uriBuilder.path("/api/usuario/fisico{id}").buildAndExpand(createdUsuarioFisico.getIdUsuario())
				.toUri();
		return ResponseEntity.created(uri).body(createdUsuarioFisico);
	}

	@PutMapping("{id}")
	public ResponseEntity<UsuarioFisico> atualizarUsuarioFisico(@PathVariable Long id,
			@RequestBody @Valid UsuarioFisico usuarioFisico) {
		UsuarioFisico usuarioFisicoUpdated = service.update(id, usuarioFisico);
		if (usuarioFisicoUpdated == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(usuarioFisicoUpdated);
	}

	@GetMapping
	public Page<UsuarioFisico> buscarTodosUsuarios(@PageableDefault Pageable pageable) {
		return service.getAll(pageable);
	}

	@GetMapping("{id}")
	public ResponseEntity<UsuarioFisico> buscarUsuarioFisico(@PathVariable Long id) {
		return ResponseEntity.of(service.findById(id));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletarUsuarioFisico(@PathVariable Long id) {
		Optional<UsuarioFisico> user = service.findById(id);
		if (user.isEmpty()) 
			return ResponseEntity.notFound().build();
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
