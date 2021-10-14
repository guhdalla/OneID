package br.com.fiap.oneid.controller.api;

import br.com.fiap.oneid.model.*;
import br.com.fiap.oneid.model.mqtt.InitializingOnDemandHolder;
import br.com.fiap.oneid.service.TokenService;
import br.com.fiap.oneid.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacao")
public class ApiTransacaoController {

    final InitializingOnDemandHolder INSTANCE = InitializingOnDemandHolder.getInstance();


    @Autowired
    private TransacaoService service;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<List<Transacao>> getAllTransacao(){
        return ResponseEntity.ok().body(service.getAll());
    }

    public ResponseEntity<?> create(@RequestBody(required = true) TransacaoPendente transacaoPendente, HttpServletRequest request){
        Usuario usuario = (Usuario) tokenService.findByToken(tokenService.extractToken(request));
        try{
            Dispositivo dispositivo = ((UsuarioJuridico) usuario)
                    .getDispositivos()
                    .stream()
                    .filter(x->x.getCdDispositivo().equals(transacaoPendente.getCodigoDispositivo()))
                    .findFirst().orElse(null);
            if(dispositivo == null) throw new RuntimeException("Dispositivo não encontrado");
            INSTANCE.setContext(transacaoPendente.getCodigoDispositivo(), transacaoPendente.getValue());
            System.out.println(INSTANCE.getContext());
            return ResponseEntity.ok().body(dispositivo);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

}
