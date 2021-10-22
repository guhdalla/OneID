package br.com.fiap.oneid.controller.api;

import br.com.fiap.oneid.model.*;
import br.com.fiap.oneid.model.mqtt.InitializingOnDemandHolder;
import br.com.fiap.oneid.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacao")
public class ApiTransacaoController {

    final InitializingOnDemandHolder INSTANCE = InitializingOnDemandHolder.getInstance();

    @Autowired
    private TransacaoService service;

    @PostMapping
    public ResponseEntity<TransacaoPendente> create(@RequestBody @Valid TransacaoPendente transacaoPendente, HttpServletRequest request){
        try{
            service.create(transacaoPendente, request);
            return ResponseEntity.ok().body(transacaoPendente);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{codigoDispositivo}")
    public ResponseEntity<Void> deleteTransacaoPendente(HttpServletRequest request, @PathVariable("codigoDispositivo") String codigoDispositivo){
        Optional<TransacaoPendente> tp = service.delete(request, codigoDispositivo, INSTANCE);
        if(tp.isEmpty()) return ResponseEntity.notFound().build();
        INSTANCE.getContext().remove(tp.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pendente")
    public List<TransacaoPendente> getTransacoesPendentes() {
    	return INSTANCE.getContext();
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> getAllTransacao(HttpServletRequest request){
        return ResponseEntity.ok().body(service.getAllTransacao(request));
    }

}
