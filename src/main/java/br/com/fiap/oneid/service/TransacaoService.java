package br.com.fiap.oneid.service;

import br.com.fiap.oneid.model.Transacao;
import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;
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
    private UsuarioRepository UsuarioRepository;


    public List<Transacao> getAll(){
        return transacaoRepository.findAll();
    }

}
