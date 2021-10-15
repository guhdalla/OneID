package br.com.fiap.oneid.controller.api;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import javax.validation.Valid;

import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.repository.UsuarioRepository;
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

	private UsuarioRepository repository;

	Map<String, Usuario> mapeamento = new HashMap<>();

	@Autowired
	public ApiUsuarioJuridicoController(UsuarioJuridicoService service, UsuarioRepository repository) {
		this.repository= repository;
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<UsuarioJuridico> cadastrarUsuario(@RequestBody @Valid UsuarioJuridico usuarioJuridico,
			UriComponentsBuilder uriBuilder) throws IOException {
		usuarioJuridico.setFotoPerfil(returnBytesDefault());
		UsuarioJuridico usuarioJuridicoSaved = service.create(usuarioJuridico);
		if(usuarioJuridicoSaved == null) return ResponseEntity.badRequest().build();
		URI uri = uriBuilder.path("/api/usuario/juridico/{id}").buildAndExpand(usuarioJuridicoSaved.getIdUsuario()).toUri();
		return ResponseEntity.created(uri).body(usuarioJuridicoSaved);
	}

	@PutMapping("{id}")
	public ResponseEntity<UsuarioJuridico> atualizarDados(@PathVariable Long id,
			@RequestBody @Valid UsuarioJuridico usuarioJuridico, @RequestParam("photo") MultipartFile photo) throws IOException {

		List<Usuario> usuarios = repository.findAll();

		verifyIfExistsImgs();
		createMapAndImgPushView(mapeamento, usuarios);

		byte[] imageByteDefault = returnBytesDefault();
		usuarioJuridico.setFotoPerfil(Objects.requireNonNull(photo.getOriginalFilename()).isEmpty() ? imageByteDefault : photo.getBytes());

		return service.findById(id).map(x -> {
			x.setEmail(usuarioJuridico.getEmail());
			x.setFotoPerfil(usuarioJuridico.getFotoPerfil());
			x.setPassword(usuarioJuridico.getPassword());
			x.setEndereco(usuarioJuridico.getEndereco());
			x.setTelefone(usuarioJuridico.getTelefone());
			UsuarioJuridico userAtualizado = service.update(x.getIdUsuario(), usuarioJuridico);
			return ResponseEntity.ok().body(userAtualizado);
		}).orElse(ResponseEntity.notFound().build());
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
