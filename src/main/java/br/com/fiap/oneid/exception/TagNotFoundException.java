package br.com.fiap.oneid.exception;

import javax.persistence.EntityNotFoundException;

public class TagNotFoundException extends EntityNotFoundException {
    public TagNotFoundException(String mensagem){
        super(mensagem);
    }
}
