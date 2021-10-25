package br.com.fiap.oneid.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Carteira;
import br.com.fiap.oneid.model.Role;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.repository.CarteiraReposiory;
import br.com.fiap.oneid.repository.RoleRepository;
import br.com.fiap.oneid.repository.UsuarioFisicoRepository;

@Service
public class UsuarioFisicoService {

	final UsuarioFisicoRepository repository;
	
	final CarteiraReposiory repositoryCarteira;
	
	final RoleRepository repositoryRole;

	@Autowired
	public UsuarioFisicoService(UsuarioFisicoRepository repository, CarteiraReposiory repositoryCarteira, RoleRepository repositoryRole) {
		this.repository = repository;
		this.repositoryCarteira = repositoryCarteira;
		this.repositoryRole = repositoryRole;
	}

	public UsuarioFisico create(UsuarioFisico usuarioFisico) {
		try {
			Optional<Role> role = repositoryRole.findByName("ROLE_FISICO");
			if(role.isEmpty()) return null;
			usuarioFisico.addRole(role.get());
			Carteira carteira = repositoryCarteira.save(new Carteira(0));
			usuarioFisico.setCarteira(carteira);
			usuarioFisico.setPassword(AuthenticationService.getPasswordEncoder().encode(usuarioFisico.getPassword()));
			return repository.save(usuarioFisico);
		} catch (Exception e) {
			return null;
		}
	}
	
	public UsuarioFisico save(UsuarioFisico usuarioFisico) {
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
		current.setPassword(updated.getPassword());
		current.setFotoPerfil(updated.getFotoPerfil());
		return current;
	}
}
