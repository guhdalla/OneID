package br.com.fiap.oneid.repository;

import br.com.fiap.oneid.model.Transacao;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("")
    List<Transacao> findByUsuarioAndUsuarioFisicoOrAndUsuarioJuridico(@PathParam("usuarioFisicao") UsuarioFisico usuarioFisico,@PathParam("usuarioFisicao") UsuarioJuridico usuarioJuridico);
}
