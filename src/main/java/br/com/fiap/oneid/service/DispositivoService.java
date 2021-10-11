package br.com.fiap.oneid.service;

import br.com.fiap.oneid.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DispositivoService {

    final
    DispositivoRepository repository;

    @Autowired
    public DispositivoService(DispositivoRepository repository) {
        this.repository = repository;
    }
}
