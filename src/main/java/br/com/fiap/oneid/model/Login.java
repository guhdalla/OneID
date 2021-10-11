package br.com.fiap.oneid.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Login {
	
	@NotBlank
	@Email
	private String username;
	
	@NotBlank
	private String password;

}
