package br.com.fiap.oneid.controller.api;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import javax.validation.Valid;

import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.repository.UsuarioRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.service.UsuarioFisicoService;

import static br.com.fiap.oneid.util.ImageTransform.*;

@RestController
@RequestMapping("/api/usuario/fisico")
public class ApiUsuarioFisicoController {

	final UsuarioFisicoService service;

	final UsuarioRepository repository;

	Map<String, Usuario> mapeamento = new HashMap<>();

	@Autowired
	public ApiUsuarioFisicoController(UsuarioFisicoService service, UsuarioRepository repository) {
		this.repository=repository;
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
	
	@PutMapping("/img/{id}")
	public ResponseEntity<String> atualizarFotoPerfil(@PathVariable Long id, @RequestParam MultipartFile photo, UriComponentsBuilder uriBuilder) throws IOException {
	
		UsuarioFisico usuarioFisico = service.findById(id).get();
		byte[] imageByteDefault = returnBytesDefault();
		usuarioFisico.setFotoPerfil(Objects.requireNonNull(photo.getOriginalFilename()).isEmpty() ? imageByteDefault : photo.getBytes());
		
		usuarioFisico = service.save(usuarioFisico);
		
		verifyIfExistsImgs();
		createMapAndImgPushView(mapeamento, List.of(usuarioFisico));
		
		URI uri = uriBuilder.path("/api/img/{linkImage}.jpg").buildAndExpand(usuarioFisico.getIdUsuario()).toUri();
		
		return ResponseEntity.ok(uri.toString());
	}

	@PutMapping("{id}")
	public ResponseEntity<UsuarioFisico> atualizarUsuarioFisico(@PathVariable Long id,
			@RequestBody @Valid UsuarioFisico usuarioFisico) throws IOException {
		UsuarioFisico userAtualizado = service.update(id, usuarioFisico);
		if(userAtualizado == null) return ResponseEntity.badRequest().build();
		return ResponseEntity.ok().body(userAtualizado);
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
