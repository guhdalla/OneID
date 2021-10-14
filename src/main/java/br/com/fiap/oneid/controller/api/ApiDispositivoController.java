package br.com.fiap.oneid.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.oneid.model.Dispositivo;
import br.com.fiap.oneid.service.DispositivoService;

@RestController
@RequestMapping("/api/dispositivo")
public class ApiDispositivoController {

	final DispositivoService service;

	@Autowired
	public ApiDispositivoController(DispositivoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Dispositivo> cadastrarDispositivo(@RequestBody @Valid Dispositivo dispositivo,
			UriComponentsBuilder uriBuilder) {
		Dispositivo dispositivoCreated = service.create(dispositivo);
		return ResponseEntity.ok(dispositivoCreated);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<Dispositivo>> buscarTodasTagsPorUsuario(@PathVariable Long id) {
		List<Dispositivo> listaDispositivo = service.findByEmpresa(id);
		if (listaDispositivo == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(listaDispositivo);
	}

	@PutMapping("/{codigoPin}")
	public ResponseEntity<Dispositivo> vincularDispositivo(@PathVariable String codigoPin,
			@RequestBody Dispositivo dispositivo) {
		Dispositivo dispositivoUpdated = service.vincular(codigoPin, dispositivo);
		if (dispositivoUpdated == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(dispositivoUpdated);
	}

	@PutMapping("/{codigoPin}/{status}")
	public ResponseEntity<Dispositivo> atualizarStatusDispositivo(@PathVariable String codigoPin,
			@PathVariable int status) {
		Dispositivo dispositivoUpdated = service.modifyStatus(codigoPin, status);
		if (dispositivoUpdated == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(dispositivoUpdated);
	}
}
