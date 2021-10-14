package br.com.fiap.oneid.controller.api;

import br.com.fiap.oneid.model.*;
import br.com.fiap.oneid.model.mqtt.InitializingOnDemandHolder;
import br.com.fiap.oneid.service.TokenService;
import br.com.fiap.oneid.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacao")
public class ApiTransacaoController {

    final InitializingOnDemandHolder INSTANCE = InitializingOnDemandHolder.getInstance();


    @Autowired
    private TransacaoService service;


    @GetMapping
    public ResponseEntity<List<Transacao>> getAllTransacao(){
        return ResponseEntity.ok().body(service.getAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TransacaoPendente transacaoPendente, HttpServletRequest request){
        try{
            Dispositivo dispositivo = service.create(transacaoPendente, request);
            boolean exist = INSTANCE.verifyContextExist(transacaoPendente);
            if(exist) throw new RuntimeException("Transacao pendente ja existe");
            INSTANCE.setContext(transacaoPendente);
            return ResponseEntity.ok().body(dispositivo);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{codigoDispositivo}")
    public ResponseEntity<Void> deleteTransacaoPendente(HttpServletRequest request, @PathVariable("codigoDispositivo") String codigoDispositivo){
        TransacaoPendente tp = service.delete(request, codigoDispositivo, INSTANCE);
        INSTANCE.getContext().remove(tp);
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping
    public List<TransacaoPendente> getTransacoesPendentes() {
    	return INSTANCE.getContext();
    }



}
