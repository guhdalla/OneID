package br.com.fiap.oneid.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Carteira;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.repository.CarteiraReposiory;
import br.com.fiap.oneid.repository.UsuarioFisicoRepository;

@Service
public class UsuarioFisicoService {

	final UsuarioFisicoRepository repository;
	
	final CarteiraReposiory repositoryCarteira;

	@Autowired
	public UsuarioFisicoService(UsuarioFisicoRepository repository, CarteiraReposiory repositoryCarteira) {
		this.repository = repository;
		this.repositoryCarteira = repositoryCarteira;
	}

	public UsuarioFisico create(UsuarioFisico usuarioFisico) {
		Carteira carteira = repositoryCarteira.save(new Carteira(0));
		usuarioFisico.setCarteira(carteira);
		return repository.save(usuarioFisico);
	}

	public Optional<UsuarioFisico> findById(Long id) {
		return repository.findById(id);
	}

	public Page<UsuarioFisico> getAll(@PageableDefault Pageable pageable) {
		return repository.findAll(pageable);
	}

	public void delete(Long id) {
		Optional<UsuarioFisico> usuarioFisico = findById(id);
		repository.delete(usuarioFisico.get());
	}

	public UsuarioFisico update(Long id, UsuarioFisico usuarioFisico) {
		Optional<UsuarioFisico> usuarioFisicoOp = findById(id);
		if(usuarioFisicoOp.isEmpty())
			return null;
		UsuarioFisico updated = updateData(usuarioFisicoOp.get(), usuarioFisico);
		return repository.save(updated);
		
		
	}

	public UsuarioFisico updateData(UsuarioFisico current, UsuarioFisico updated) {
		current.setEmail(updated.getEmail());
		current.setTelefone(updated.getTelefone());
		current.setSenha(updated.getSenha());
		current.setFotoPerfil(updated.getFotoPerfil());
		return current;
	}
}
