package br.com.fiap.oneid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.oneid.model.UsuarioJuridico;

@Repository
public interface UsuarioJuridicoRepository extends JpaRepository<UsuarioJuridico, Long> {

}
