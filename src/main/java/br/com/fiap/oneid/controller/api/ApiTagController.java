package br.com.fiap.oneid.controller.api;

import br.com.fiap.oneid.model.Tag;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import br.com.fiap.oneid.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

	@PutMapping("/{id}")
	public ResponseEntity<Tag> atualizarTag(@PathVariable Long id, @RequestBody @Valid Tag tag) {
		return ResponseEntity.ok().body(service.update(id, tag));
	}

	@PutMapping("/{id}/{status}")
	public ResponseEntity<Tag> atualizarStatusTag(@PathVariable Long id, @PathVariable int status) {
		return ResponseEntity.ok().body(service.modifyStatus(id, status));
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
