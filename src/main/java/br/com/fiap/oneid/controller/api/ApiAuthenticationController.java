package br.com.fiap.oneid.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.oneid.model.Login;
import br.com.fiap.oneid.service.TokenService;

@RestController
@RequestMapping("/api/log")
public class ApiAuthenticationController {
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthenticationManager authManeger;

	@PostMapping("/in")
	public ResponseEntity<String> login(@RequestBody @Valid Login login) {
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
		try {
			Authentication authenticate = authManeger.authenticate(authentication);
			String token = tokenService.getToken(authenticate);
			return ResponseEntity.ok(token);
		} catch (AuthenticationException	 e) {
			return ResponseEntity.badRequest().build();		}
	}
}
