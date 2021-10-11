package br.com.fiap.oneid.controller.advice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ValidationControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<ValidationFieldError> handle(MethodArgumentNotValidException e) {
		log.error("Erro");
		List<ValidationFieldError> list  = new ArrayList<ValidationFieldError>();
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		errors.forEach(erro -> {
			list.add(new ValidationFieldError(erro.getField(), erro.getDefaultMessage()));
		});
		return list;
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ValidationFieldError handleUnique(ConstraintViolationException e) {
		log.error("Erro");
		return new ValidationFieldError(e.getSQLException().getMessage());
	}
}
