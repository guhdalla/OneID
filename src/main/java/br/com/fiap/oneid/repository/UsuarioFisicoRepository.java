package br.com.fiap.oneid.repository;

import org.springframework.data.jpa.mapping.JpaPersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;

public interface UsuarioFisicoRepository extends JpaRepository<UsuarioFisico, Long> {
	

}
