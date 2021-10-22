package br.com.fiap.oneid.repository;

import br.com.fiap.oneid.model.Dispositivo;
import br.com.fiap.oneid.model.UsuarioJuridico;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

	List<Dispositivo> findByUsuarioJuridico(UsuarioJuridico usuarioJuridico);

	Optional<Dispositivo> findByCodigoPin(String codigoPin);

	@Query("select u from Dispositivo u where u.cdDispositivo = :cdDispositivo")
	Optional<Dispositivo> findByCdDispositivo(@Param("cdDispositivo") String cdDispositivo);
}
