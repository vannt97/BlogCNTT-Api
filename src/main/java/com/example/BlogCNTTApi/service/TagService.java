package com.example.BlogCNTTApi.service;

import com.example.BlogCNTTApi.entity.CategoryEntity;
import com.example.BlogCNTTApi.entity.TagEntity;
import com.example.BlogCNTTApi.exceptions.ResourceNotFoundException;
import com.example.BlogCNTTApi.repository.TagRepository;
import com.example.BlogCNTTApi.util.StringHepler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TagService implements CRUDService<TagEntity, String> {

    @Autowired
    TagRepository repository;

    @Transactional
    @Override
    public void create(String param) {
        TagEntity newTag = new TagEntity();

        newTag.setName(param);
        newTag.setCreatedAt(new Date());
        newTag.setModifiedAt(new Date());
        newTag.setSlug(StringHepler.toSlug(param));
        repository.save(newTag);
    }

    @Transactional
    @Override
    public void edit(long id, String param) {
        Optional<TagEntity> tag = repository.findById(id);
        if(tag.isPresent()){
            TagEntity tagEdit = tag.get();
            tagEdit.setName(param);
            tagEdit.setModifiedAt(new Date());
            tagEdit.setSlug(StringHepler.toSlug(param));
            repository.save(tagEdit);
        }else {
            throw  new ResourceNotFoundException("Không có id tag " + id + " trong database");
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        Optional<TagEntity> tag = repository.findById(id);
        if(tag.isPresent()){
            repository.delete(tag.get());
        }else {
            throw  new ResourceNotFoundException("Không có id tag " + id + " trong database");
        }
    }

    @Override
    public List<TagEntity> datas() {
        return repository.findAll();
    }

    @Override
    public TagEntity data(long id) {
        return null;
    }
}
