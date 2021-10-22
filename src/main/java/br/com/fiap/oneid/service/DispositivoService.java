package br.com.fiap.oneid.service;

import br.com.fiap.oneid.model.Dispositivo;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.repository.DispositivoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DispositivoService {

	final DispositivoRepository repository;

	final UsuarioJuridicoService serviceJuridico;

	@Autowired
	public DispositivoService(DispositivoRepository repository, UsuarioJuridicoService serviceJuridico) {
		this.repository = repository;
		this.serviceJuridico = serviceJuridico;
	}

	public Dispositivo create(Dispositivo dispositivo) {
		return repository.save(dispositivo);
	}

	public List<Dispositivo> findByEmpresa(Long id) {
		Optional<UsuarioJuridico> usuarioJuridico = serviceJuridico.findById(id);
		if (usuarioJuridico.isEmpty())
			return null;
		return repository.findByUsuarioJuridico(usuarioJuridico.get());
	}

	public Dispositivo vincular(String codigoPin, Long idUsuario) {
		Optional<Dispositivo> dispositivoOp = repository.findByCodigoPin(codigoPin);
		if (dispositivoOp.isEmpty())
			return null;
		if (dispositivoOp.get().getStatusDispositivo() != 0)
			return null;
		Optional<UsuarioJuridico> userOp = serviceJuridico.findById(idUsuario);
		if (userOp.isEmpty())
			return null;
		dispositivoOp.get().setStatusDispositivo(1);
		dispositivoOp.get().setUsuarioJuridico(userOp.get());
		return create(dispositivoOp.get());
	}

	public Dispositivo modifyStatus(String codigoPin, int status) {
		Optional<Dispositivo> dispositivo = repository.findByCodigoPin(codigoPin);
		if (dispositivo.isEmpty())
			return null;
		dispositivo.get().setStatusDispositivo(status);;
		return create(dispositivo.get());
	}
	
	public Optional<Dispositivo> findByCdDispositivo(String cdDispositivo) {
		return repository.findByCdDispositivo(cdDispositivo);
	}
}
