package br.com.fiap.oneid.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.oneid.model.Role;
import br.com.fiap.oneid.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class ApiRoleController {
	
	final RoleService service;

	@Autowired
	public ApiRoleController(RoleService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<Role> create(@RequestBody @Valid Role role) {
		Role roleCreated = service.create(role);
		return ResponseEntity.ok(roleCreated);
	}

}
