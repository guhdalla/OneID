package br.com.fiap.oneid.controller.api;

import br.com.fiap.oneid.model.Tag;
import br.com.fiap.oneid.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@Api(tags="Endpoint Tag")
public class ApiTagController {

    final
    TagService service;

    @Autowired
    public ApiTagController(TagService service) {
        this.service = service;
    }

    @ApiOperation(value = "Cadastrar Tag")
    @PostMapping
    public ResponseEntity<Tag> cadastrarTag(@RequestBody Tag tag){
        Tag createdTag = service.create(tag);
        return ResponseEntity.ok().body(createdTag);
    }

    @ApiOperation(value = "Atualizar Tag")
    @PutMapping("/{id}")
    public ResponseEntity<Tag> atualizarTag(@PathVariable Long id, @RequestBody Tag tag){
        Tag updatedTag = service.update(id, tag);
        return ResponseEntity.ok().body(updatedTag);
    }

    @ApiOperation(value = "Buscar todas Tags")
    @GetMapping
    public ResponseEntity<List<Tag>> buscarTodasTags(){
        List<Tag> listTags = service.getAll();
        return ResponseEntity.ok().body(listTags);
    }

    @ApiOperation(value = "Deletar Tag")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTag(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Atualizar Tag")
    @PostMapping("/status/tag/{id}/{status}")
    public ResponseEntity<Tag> atualizarStatusTag(@PathVariable Long id, @PathVariable int status){
        Tag tagStatusModified = service.modifyStatus(id, status);
        return ResponseEntity.ok().body(tagStatusModified);
    }
}
