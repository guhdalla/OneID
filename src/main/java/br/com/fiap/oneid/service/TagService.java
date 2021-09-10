package br.com.fiap.oneid.service;

import br.com.fiap.oneid.model.Tag;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import br.com.fiap.oneid.repository.TagRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

	final TagRepository repository;

	@Autowired
	public TagService(TagRepository repository) {
		this.repository = repository;
	}

	public Tag create(Tag tag) {
		return repository.save(tag);
	}

	public Optional<Tag> findById(Long id) {
		return repository.findById(id);
	}

	public Page<Tag> getAll(@PageableDefault Pageable pageable) {
		return repository.findAll(pageable);
	}

	public void delete(Tag tag) {
		repository.delete(tag);
	}

	public Tag modifyStatus(Long id, int onVerify) {
		Optional<Tag> tag = findById(id);
		tag.get().setNumeroStatus(onVerify);
		return update(id, tag.get());
	}

	public Tag update(Long id, Tag tag) {
		Tag updatedTag = updateData(findById(id).get(), tag);
		return repository.save(updatedTag);
	}

	public Tag updateData(Tag current, Tag updated) {
		current.setCodigoPin(updated.getCodigoPin());
		current.setCodigoTag(updated.getCodigoTag());
		current.setNumeroStatus(updated.getNumeroStatus());
		return current;
	}
}
