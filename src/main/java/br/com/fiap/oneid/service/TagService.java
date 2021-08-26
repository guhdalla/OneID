package br.com.fiap.oneid.service;

import br.com.fiap.oneid.exception.TagNotFoundException;
import br.com.fiap.oneid.model.Tag;
import br.com.fiap.oneid.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    final
    TagRepository repository;

    @Autowired
    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public Tag create(Tag tag) {
        return repository.save(tag);
    }

    public Tag findById(Long id){
        return repository.findById(id).orElseThrow(() -> new TagNotFoundException("Tag com id " + id + " não encontrada"));
    }

    public List<Tag> getAll(){
        return repository.findAll();
    }

    public void delete(Long id){
        Tag tag = findById(id);
        repository.delete(tag);
    }

    public Tag modifyStatus(Long id, int onVerify){
        Tag tag = findById(id);
        tag.setNumeroStatus(onVerify);
        return update(id, tag);
    }

    public Tag update(Long id, Tag tag){
        Tag updatedTag = updateData(findById(id), tag);
        return repository.save(updatedTag);
    }


    public Tag updateData(Tag current, Tag updated){
        current.setCodigoPin(updated.getCodigoPin());
        current.setCodigoTag(updated.getCodigoTag());
        current.setIdTag(updated.getIdTag());
        current.setNumeroStatus(updated.getNumeroStatus());
        return current;
    }
}
