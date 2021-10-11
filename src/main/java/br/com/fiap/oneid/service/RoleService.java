package br.com.fiap.oneid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Role;
import br.com.fiap.oneid.repository.RoleRepository;

@Service
public class RoleService {

	final RoleRepository repository;

	@Autowired
	public RoleService(RoleRepository repository) {
		this.repository = repository;
	}
	
	public Role create(Role role) {
		return repository.save(role);
	}
}
