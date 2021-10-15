package br.com.fiap.oneid.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Carteira;
import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;
import br.com.fiap.oneid.repository.CarteiraReposiory;

@Service
public class CarteiraService {
	
	final CarteiraReposiory repository;
	final TokenService tokenService;

	@Autowired
	public CarteiraService(CarteiraReposiory repository, TokenService tokenService) {
		this.repository = repository;
		this.tokenService = tokenService;
	}

	public Carteira alterarSaldo(Long id, float valor) {
		Optional<Carteira> carteira = repository.findById(id);
		if(carteira.isEmpty())
			return null;
		if(valor < 0 && carteira.get().getSaldo() > valor * -1) {
			carteira.get().setSaldo(carteira.get().getSaldo() - valor);
		} else if(valor >= 0) {
			carteira.get().setSaldo(carteira.get().getSaldo() + valor);
		} else {
			return null;
		}
		return repository.save(carteira.get());
	}

}
