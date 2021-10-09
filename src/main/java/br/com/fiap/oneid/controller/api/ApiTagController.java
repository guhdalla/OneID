package br.com.fiap.oneid.controller.api;

import br.com.fiap.oneid.model.Tag;
import br.com.fiap.oneid.model.Usuario;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import br.com.fiap.oneid.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tag")
public class ApiTagController {

	final TagService service;

	@Autowired
	public ApiTagController(TagService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Tag> cadastrarTag(@RequestBody @Valid Tag tag, UriComponentsBuilder uriBuilder) {
		Tag createdTag = service.create(tag);
		URI uri = uriBuilder.path("/api/usuario/fisico{id}").buildAndExpand(createdTag.getIdTag()).toUri();
		return ResponseEntity.created(uri).body(createdTag);
	}

	@GetMapping
	public Page<Tag> buscarTodasTags(@PageableDefault Pageable pageable) {
		return service.getAll(pageable);
	}
	
	@GetMapping("/{id}/{numeroStatus}")
	public List<Tag> buscarTodasTagsPorUsuarioAndNumeroStatus(@PathVariable Long id, @PathVariable int numeroStatus) {
		return service.findByIdUsuarioAndNumeroStatus(id, numeroStatus);
	}

	@GetMapping("/{id}")
	public List<Tag> buscarTodasTagsPorUsuario(@PathVariable Long id) {
		return service.findByIdUsuario(id);
	}

	@PutMapping("/{codigoPin}")
	public ResponseEntity<Tag> atualizarTag(@PathVariable String codigoPin, @RequestBody Tag tag) {
		Tag tagUpdated = service.vincular(codigoPin, tag);
		if(tagUpdated == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(tagUpdated);
	}

	@PutMapping("/{codigoPin}/{status}")
	public ResponseEntity<Tag> atualizarStatusTag(@PathVariable String codigoPin, @PathVariable int status) {
		Tag tagUpdated = service.modifyStatus(codigoPin, status);
		if(tagUpdated == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(tagUpdated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarTag(@PathVariable Long id) {
		Optional<Tag> tag = service.findById(id);
		if (tag.isEmpty()) 
			return ResponseEntity.notFound().build();
		service.delete(tag.get());
		return ResponseEntity.ok().build();
	}
}
