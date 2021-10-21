package br.com.fiap.oneid.repository;

import br.com.fiap.oneid.model.Tag;
import br.com.fiap.oneid.model.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

	List<Tag> findByUsuario(Usuario usuario);

	Optional<Tag> findByCodigoPin(String codigoPin);

	List<Tag> findByUsuarioAndNumeroStatus(Usuario usuario, int numeroStatus);

	Optional<Tag> findByCodigoTag(String idTag);
}
