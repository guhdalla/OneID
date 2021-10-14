package br.com.fiap.oneid.repository;

import br.com.fiap.oneid.model.Transacao;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	List<Transacao> findByUsuarioJuridico(UsuarioJuridico usuarioJuridico);

	List<Transacao> findByUsuarioFisico(UsuarioFisico usuarioFisico);
}
