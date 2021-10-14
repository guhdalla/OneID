package br.com.fiap.oneid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.repository.UsuarioJuridicoRepository;
import br.com.fiap.oneid.model.Atividade;
import br.com.fiap.oneid.model.Carteira;
import br.com.fiap.oneid.model.Role;
import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.repository.CarteiraReposiory;
import br.com.fiap.oneid.repository.RoleRepository;

@Service
public class UsuarioJuridicoService {
	
	
	final UsuarioJuridicoRepository repo;
	
	
	final CarteiraReposiory repositoryCarteira;
	
	final RoleRepository repositoryRole;
	
	@Autowired
	public UsuarioJuridicoService(UsuarioJuridicoRepository repository, CarteiraReposiory repositoryCarteira, RoleRepository repositoryRole) {
		this.repo = repository;
		this.repositoryCarteira = repositoryCarteira;
		this.repositoryRole = repositoryRole;
	}
	
	public UsuarioJuridico create(UsuarioJuridico usuarioJuridico) {
		try {
			Optional<Role> role = repositoryRole.findByName("ROLE_JURIDICO");
			if(role.isEmpty()) return null;
			usuarioJuridico.addRole(role.get());
			Carteira carteira = repositoryCarteira.save(new Carteira(0));
			usuarioJuridico.setCarteira(carteira);
			usuarioJuridico.setPassword(AuthenticationService.getPasswordEncoder().encode(usuarioJuridico.getPassword()));
			return repo.save(usuarioJuridico);
		} catch (Exception e) {
			return null;
		}
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

	public List<UsuarioJuridico> getAll(){
		return repo.findAll();
	}

	
	public UsuarioJuridico update(Long id, UsuarioJuridico usuario) {
		Optional<UsuarioJuridico> userOptional = repo.findById(id);
		UsuarioJuridico u = userOptional.get();
		u.setEmail(usuario.getEmail());
		u.setFotoPerfil(usuario.getFotoPerfil());
		u.setPassword(usuario.getPassword());
		u.setEndereco(usuario.getEndereco());
		u.setTelefone(usuario.getTelefone());
		u.setTotalEstabelecimento(usuario.getTotalEstabelecimento());
		return repo.save(u);	
	}
	
	public List<Atividade> getAtividades(Usuario usuario) {
		Optional<UsuarioJuridico> usuarioJuridico = findById(usuario.getIdUsuario());
		if(usuarioJuridico.isEmpty()) return null;
		return usuarioJuridico.get().getAtividades();
	}
	
	public void updateTotalEstabeleciemnto(Usuario usuario, int totalEstabelecimento) {
		Optional<UsuarioJuridico> usuarioJuridico = findById(usuario.getIdUsuario());
		if(usuarioJuridico.isEmpty()) return;
		usuarioJuridico.get().setTotalEstabelecimento(totalEstabelecimento);
		update(usuario.getIdUsuario(), usuarioJuridico.get());
	}
	
	public int getTotalEstabelecimento(Usuario usuario) {
		Optional<UsuarioJuridico> usuarioJuridico = findById(usuario.getIdUsuario());
		if(usuarioJuridico.isEmpty()) return 0;
		return usuarioJuridico.get().getTotalEstabelecimento();
	}
}
