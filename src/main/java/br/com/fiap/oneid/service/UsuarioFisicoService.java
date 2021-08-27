package br.com.fiap.oneid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		Carteira carteira = repositoryCarteira.save(new Carteira());
		usuarioFisico.setCarteira(carteira);
		return repository.save(usuarioFisico);
	}

	public Optional<UsuarioFisico> findById(Long id) {
		return repository.findById(id);
	}

	public List<UsuarioFisico> getAll() {
		return repository.findAll();
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
		current.setPrimeiroNome(updated.getPrimeiroNome());
		current.setSobrenome(updated.getSobrenome());
		current.setDataNascimento(updated.getDataNascimento());
		current.setEmail(updated.getEmail());
		current.setTelefone(updated.getTelefone());
		current.setSenha(updated.getSenha());
		current.setFotoPerfil(updated.getFotoPerfil());
		current.setCpf(updated.getCpf());
		current.setCarteira(updated.getCarteira());
		current.setAtividades(updated.getAtividades());
		current.setTransacoes(updated.getTransacoes());
		return current;
	}
}
