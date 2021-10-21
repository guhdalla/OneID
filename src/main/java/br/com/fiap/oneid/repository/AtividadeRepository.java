package br.com.fiap.oneid.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long>{

	List<Atividade> findByUsuarioJuridico(UsuarioJuridico usuarioJuridico);

	List<Atividade> findByUsuarioFisico(UsuarioFisico usuarioFisico);

	List<Atividade> findByUsuarioJuridicoAndUsuarioFisico(UsuarioJuridico usuarioJuridico, UsuarioFisico usuarioFisico,
			Sort by);

}
