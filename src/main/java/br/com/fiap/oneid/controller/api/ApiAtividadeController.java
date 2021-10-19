package br.com.fiap.oneid.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.Transacao;
import br.com.fiap.oneid.service.AtividadeService;

@RestController
@RequestMapping("/api/atividade")
public class ApiAtividadeController {

	@Autowired
	private AtividadeService service;
	
	@GetMapping
    public ResponseEntity<List<Atividade>> getAllAtividades(HttpServletRequest request){
        return ResponseEntity.ok().body(service.getAllTransacao(request));
    }
	
}
