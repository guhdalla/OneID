package br.com.fiap.oneid.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario findById(Long id) {
		Optional<Usuario> usuario = repository.findById(id);
		if (usuario.isEmpty()) return null;
		return usuario.get();
	}
}
