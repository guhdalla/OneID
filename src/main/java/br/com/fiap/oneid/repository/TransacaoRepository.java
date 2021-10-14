package br.com.fiap.oneid.repository;

import br.com.fiap.oneid.model.Transacao;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("select foo from Transacao foo where foo.usuarioFisico=:usuarioFisico OR foo.usuarioJuridico=:usuarioJuridico")
    List<Transacao> findByUsuarioJuridicoOrUsuarioFisico(@Param("usuarioJuridico") UsuarioJuridico usuarioJuridico,
                                                         @Param("usuarioFisico") UsuarioFisico usuarioFisico);
}
