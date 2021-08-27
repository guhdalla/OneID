package br.com.fiap.oneid.controller.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.service.UsuarioFisicoService;

@RestController
@RequestMapping("/api/usuario/fisico")
public class ApiUsuarioFisicoController {

    final
    UsuarioFisicoService service;

    @Autowired
    public ApiUsuarioFisicoController(UsuarioFisicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioFisico> cadastrarUsuarioFisico(@RequestBody UsuarioFisico usuarioFisico, UriComponentsBuilder uriBuilder){
    	UsuarioFisico createdUsuarioFisico = service.create(usuarioFisico);
    	URI uri = uriBuilder.path("/api/usuario/fisico{id}").buildAndExpand(createdUsuarioFisico.getIdUsuario()).toUri();
        return ResponseEntity.created(uri).body(createdUsuarioFisico);
    }

    @PutMapping("{id}")
    public ResponseEntity<UsuarioFisico> atualizarUsuarioFisico(@PathVariable Long id, @RequestBody UsuarioFisico usuarioFisico){
    	UsuarioFisico usuarioFisicoUpdated = service.update(id, usuarioFisico);
    	if(usuarioFisicoUpdated == null)
    		return ResponseEntity.notFound().build();
    	return ResponseEntity.ok().body(usuarioFisicoUpdated);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioFisico>> buscarTodosUsuarios(){
        List<UsuarioFisico> listUsuarioFisico = service.getAll();
        return ResponseEntity.ok().body(listUsuarioFisico);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<UsuarioFisico> buscarUsuarioFisico(@PathVariable Long id){
    	Optional<UsuarioFisico> usuarioFisico = service.findById(id);
		if (usuarioFisico.isPresent())
			return ResponseEntity.ok(usuarioFisico.get());
		return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarUsuarioFisico(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}		
