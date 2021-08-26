package br.com.fiap.oneid.repository;

import br.com.fiap.oneid.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
