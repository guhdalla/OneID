package br.com.fiap.oneid.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.oneid.model.Carteira;
import br.com.fiap.oneid.service.CarteiraService;

@RestController
@RequestMapping("/api/carteira")
public class ApiCarteiraController {
	
	final CarteiraService service;

	@Autowired
	public ApiCarteiraController(CarteiraService service) {
		this.service = service;
	}
	
	@PutMapping("/{id}/{valor}")
	public ResponseEntity<Carteira> alterarSaldo(@PathVariable Long id, @PathVariable float valor) {
		Carteira carteira = service.alterarSaldo(id, valor);
		if (carteira == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(carteira);
	}

}
