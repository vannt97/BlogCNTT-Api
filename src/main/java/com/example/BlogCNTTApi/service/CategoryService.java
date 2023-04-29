package com.example.BlogCNTTApi.service;

import com.example.BlogCNTTApi.entity.CategoryEntity;
import com.example.BlogCNTTApi.exceptions.ResourceNotFoundException;
import com.example.BlogCNTTApi.repository.CategoryRepository;
import com.example.BlogCNTTApi.util.StringHepler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.List;

@Service
public class CategoryService {


    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public void create(String name){
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setName(name);
        newCategory.setCreatedAt(new Date());
        newCategory.setModifiedAt(new Date());
        newCategory.setSlug(StringHepler.toSlug(name));
        categoryRepository.save(newCategory);
    }

    @Transactional
    public void delete(long id){
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if(category.isPresent()){
            categoryRepository.delete(category.get());
        }else {
            throw  new ResourceNotFoundException("Không có id category " + id + " trong database");
        }
    }

    @Transactional
    public void edit(long id, String name){
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if(category.isPresent()){
            CategoryEntity categoryEdit = category.get();
            categoryEdit.setName(name);
            categoryEdit.setSlug(StringHepler.toSlug(name));
            categoryEdit.setModifiedAt(new Date());
            categoryRepository.save(categoryEdit);
        }else {
            throw  new ResourceNotFoundException("Không có id category " + id + " trong database");
        }
    }


    public List<CategoryEntity> categories(){
        return categoryRepository.findAll();
    }

}
