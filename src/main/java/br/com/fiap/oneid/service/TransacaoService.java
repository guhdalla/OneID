package br.com.fiap.oneid.service;

import br.com.fiap.oneid.model.*;
import br.com.fiap.oneid.model.mqtt.InitializingOnDemandHolder;
import br.com.fiap.oneid.repository.TransacaoRepository;
import br.com.fiap.oneid.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TokenService tokenService;

    public List<Transacao> getAll(){
        return transacaoRepository.findAll();
    }

    public Dispositivo create(TransacaoPendente transacaoPendente, HttpServletRequest request){
        Usuario usuario = getUsuarioByToken(request);
        Dispositivo dispositivo = verifyDispositivo(usuario, transacaoPendente.getCodigoDispositivo());
            if(dispositivo == null) throw new RuntimeException("Dispositivo não encontrado");
        return dispositivo;
    }

    public Optional<TransacaoPendente> delete(HttpServletRequest request, String codigoDispositivo, InitializingOnDemandHolder INSTANCE){
        Dispositivo dispositivo = verifyDispositivo(getUsuarioByToken(request), codigoDispositivo);
        if(dispositivo==null) throw new RuntimeException("Dispositivo não encontrado");
        return INSTANCE.getContext().stream().filter(x->x.getCodigoDispositivo().equals(codigoDispositivo)).findFirst();
    }

    public Dispositivo verifyDispositivo(Usuario usuario, String codigoDispositivo){
        return ((UsuarioJuridico) usuario)
                .getDispositivos()
                .stream()
                .filter(x->x.getCdDispositivo().equals(codigoDispositivo))
                .findFirst().orElse(null);
    }

    public Usuario getUsuarioByToken(HttpServletRequest request){
        return (Usuario) tokenService.findByToken(tokenService.extractToken(request));
    }

    public List<Transacao> getAllTransacao(HttpServletRequest request){
        UsuarioFisico uF = (UsuarioFisico) getUsuarioByToken(request);
        UsuarioJuridico uJ = (UsuarioJuridico) getUsuarioByToken(request);
        return transacaoRepository.findByUsuarioJuridicoOrUsuarioFisico(uJ, uF);
    }

}
