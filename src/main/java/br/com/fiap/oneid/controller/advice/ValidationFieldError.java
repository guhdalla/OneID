package br.com.fiap.oneid.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationFieldError {
	
	public ValidationFieldError(String error) {
		super();
		this.error = error;
	}
	
	private String field;
	private String error;
}
