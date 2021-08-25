package br.com.fiap.oneid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.oneid.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
