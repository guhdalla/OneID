package br.com.fiap.oneid.service;

import br.com.fiap.oneid.model.Tag;
import br.com.fiap.oneid.model.Usuario;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import br.com.fiap.oneid.repository.TagRepository;
import br.com.fiap.oneid.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

	final TagRepository repository;
	final UsuarioRepository repositoryUsuario;
	final TokenService tokenService;


	@Autowired
	public TagService(TagRepository repository, UsuarioRepository repositoryUsuario, TokenService tokenService) {
		this.repository = repository;
		this.repositoryUsuario = repositoryUsuario;
		this.tokenService = tokenService;
	}

	public Tag create(Tag tag) {
		return repository.save(tag);
	}

	public Optional<Tag> findById(Long id) {
		return repository.findById(id);
	}

	public List<Tag> findByIdUsuario(Long id) {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(id);
		return repository.findByUsuario(usuario);
	}
	
	public Page<Tag> getAll(@PageableDefault Pageable pageable) {
		return repository.findAll(pageable);
	}

	public void delete(Tag tag) {
		repository.delete(tag);
	}

	public Tag modifyStatus(String codigoPin, int onVerify) {
		Optional<Tag> tag = repository.findByCodigoPin(codigoPin);
		if(tag.isEmpty())
			return null;
		tag.get().setNumeroStatus(onVerify);
		return update(codigoPin, tag.get());
	}

	public Tag update(String codigoPin, Tag tag) {
		Tag updatedTag = updateData(repository.findByCodigoPin(codigoPin).get(), tag);
		return repository.save(updatedTag);
	}

	public Tag updateData(Tag current, Tag updated) {
		current.setUsuario(updated.getUsuario());;
		current.setNumeroStatus(updated.getNumeroStatus());
		return current;
	}

	public Tag vincular(String codigoPin, HttpServletRequest request) {
		Optional<Tag> tagOp = repository.findByCodigoPin(codigoPin);
		if(tagOp.isEmpty())
			return null;
		if(tagOp.get().getNumeroStatus() != 0)
			return null;
		Usuario usuario = new Usuario();
		try {
			usuario = (Usuario) tokenService.findByToken(tokenService.extractToken(request));
		} catch (Exception e) {
			usuario = null;
		}
		if(usuario == null)
			return null;
		tagOp.get().setNumeroStatus(1);
		tagOp.get().setUsuario(usuario);
		return create(tagOp.get());
	}

	public List<Tag> findByIdUsuarioAndNumeroStatus(Long id, int numeroStatus) {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(id);
		return repository.findByUsuarioAndNumeroStatus(usuario, numeroStatus);
	}
}
