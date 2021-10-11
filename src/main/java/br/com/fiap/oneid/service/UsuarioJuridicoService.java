package br.com.fiap.oneid.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.repository.UsuarioJuridicoRepository;
import br.com.fiap.oneid.model.Carteira;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.repository.CarteiraReposiory;

@Service
public class UsuarioJuridicoService {
	
	
	final UsuarioJuridicoRepository repo;
	
	
	final CarteiraReposiory repositoryCarteira;
	
	@Autowired
	public UsuarioJuridicoService(UsuarioJuridicoRepository repository, CarteiraReposiory repositoryCarteira) {
		this.repo = repository;
		this.repositoryCarteira = repositoryCarteira;
	}
	
	public UsuarioJuridico create(UsuarioJuridico usuarioJuridico) {
		Carteira carteira = repositoryCarteira.save(new Carteira(0));
		usuarioJuridico.setCarteira(carteira);
		usuarioJuridico.setPassword(AuthenticationService.getPasswordEncoder().encode(usuarioJuridico.getPassword()));
		return repo.save(usuarioJuridico);
	}
	
	public Optional<UsuarioJuridico> findById(Long id){
		return repo.findById(id);
	}
	
	public Page<UsuarioJuridico> getAll(@PageableDefault Pageable pageable){
		return repo.findAll(pageable);
	}
	
	public void delete(Long id) {
		Optional<UsuarioJuridico> usuarioJuridico = repo.findById(id);
		repo.delete(usuarioJuridico.get());
	}

	
	public UsuarioJuridico update(Long id, UsuarioJuridico usuario) {
		Optional<UsuarioJuridico> userOptional = repo.findById(id);
		UsuarioJuridico u = userOptional.get();
		u.setEmail(usuario.getEmail());
		u.setFotoPerfil(usuario.getFotoPerfil());
		u.setPassword(usuario.getPassword());
		u.setEndereco(usuario.getEndereco());
		u.setTelefone(usuario.getTelefone());
		return repo.save(u);	
	}
}
