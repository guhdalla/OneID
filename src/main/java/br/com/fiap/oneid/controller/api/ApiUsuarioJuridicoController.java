package br.com.fiap.oneid.controller.api;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import javax.validation.Valid;

import br.com.fiap.oneid.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.service.UsuarioJuridicoService;

import static br.com.fiap.oneid.util.ImageTransform.*;

@RestController
@RequestMapping("/api/usuario/juridico")
public class ApiUsuarioJuridicoController {

	private UsuarioJuridicoService service;
	
	Map<String, Usuario> mapeamento = new HashMap<>();
	
	@Autowired
	public ApiUsuarioJuridicoController(UsuarioJuridicoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<UsuarioJuridico> cadastrarUsuario(@RequestBody @Valid UsuarioJuridico usuarioJuridico,
			UriComponentsBuilder uriBuilder) throws IOException {
		
		UsuarioJuridico usuarioJuridicoSaved = service.create(usuarioJuridico);
		if(usuarioJuridicoSaved == null) return ResponseEntity.badRequest().build();
		URI uri = uriBuilder.path("/api/usuario/juridico/{id}").buildAndExpand(usuarioJuridicoSaved.getIdUsuario()).toUri();
		return ResponseEntity.created(uri).body(usuarioJuridicoSaved);
	}
	
	@PutMapping("/img/{id}")
	public ResponseEntity<String> atualizarFotoPerfil(@PathVariable Long id, @RequestParam MultipartFile photo, UriComponentsBuilder uriBuilder) throws IOException {
	
		UsuarioJuridico usuarioJuridico = service.findById(id).get();
		byte[] imageByteDefault = returnBytesDefault();
		usuarioJuridico.setFotoPerfil(Objects.requireNonNull(photo.getOriginalFilename()).isEmpty() ? imageByteDefault : photo.getBytes());
		
		usuarioJuridico = service.save(usuarioJuridico);
		
		verifyIfExistsImgs();
		createMapAndImgPushView(mapeamento, List.of(usuarioJuridico));
		
		URI uri = uriBuilder.path("/api/img/{linkImage}.jpg").buildAndExpand(usuarioJuridico.getIdUsuario()).toUri();

		return ResponseEntity.ok(uri.toString());
	}
	

	@PutMapping("{id}")
	public ResponseEntity<UsuarioJuridico> atualizarDados(@PathVariable Long id,
			@RequestBody @Valid UsuarioJuridico usuarioJuridico) throws IOException {

			UsuarioJuridico userAtualizado = service.update(id, usuarioJuridico);
			if(userAtualizado == null) return ResponseEntity.badRequest().build();
			return ResponseEntity.ok().body(userAtualizado);
	}


	@DeleteMapping("{id}")
	public ResponseEntity<UsuarioJuridico> apagarConta(@PathVariable Long id) {
		Optional<UsuarioJuridico> user = service.findById(id);
		if (user.isEmpty())
			return ResponseEntity.notFound().build();
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("{id}")
	public ResponseEntity<UsuarioJuridico> consultarUsuario(@PathVariable Long id) {
		return ResponseEntity.of(service.findById(id));
	}
	
	@GetMapping
	public Page<UsuarioJuridico> buscarTodosUsuarios(@PageableDefault Pageable pageable) {
		return service.getAll(pageable);
	}
}
