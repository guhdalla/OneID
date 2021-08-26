package br.com.fiap.oneid.repository;

import br.com.fiap.oneid.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
}
