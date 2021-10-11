package br.com.fiap.oneid.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Carteira;
import br.com.fiap.oneid.repository.CarteiraReposiory;

@Service
public class CarteiraService {
	
	final CarteiraReposiory repository;

	@Autowired
	public CarteiraService(CarteiraReposiory repository) {
		this.repository = repository;
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
