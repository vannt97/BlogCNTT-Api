package com.example.BlogCNTTApi.service;

import com.example.BlogCNTTApi.dto.PostDTO;
import com.example.BlogCNTTApi.entity.*;
import com.example.BlogCNTTApi.exceptions.ResourceNotFoundException;
import com.example.BlogCNTTApi.repository.CategoryRepository;
import com.example.BlogCNTTApi.repository.PostRepository;
import com.example.BlogCNTTApi.repository.TagRepository;
import com.example.BlogCNTTApi.repository.UserRepository;
import com.example.BlogCNTTApi.util.StringHepler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserService userService;

    @Transactional
    public void create(PostDTO post){

        PostEntity newPost = new PostEntity();
        newPost.setCreatedAt(new Date());
        newPost.setModifiedAt(new Date());
        repository.save(this.handlePost(post,newPost));
    }

    @Transactional
    public void edit(PostDTO post, String slug){
        Optional<PostEntity> epost = repository.findBySlug(slug);
        if(epost.isPresent()){
            PostEntity editPost = epost.get();
            List<Long> listLongCategory = editPost.getCategories().stream().map(CategoryEntity::getId).collect(Collectors.toList());
            listLongCategory.forEach(editPost::removeCategory);

            List<Long> listLongTag = editPost.getTags().stream().map(TagEntity::getId).collect(Collectors.toList());
            listLongTag.forEach(editPost::removeTag);

            editPost.setModifiedAt(new Date());
            repository.save(this.handlePost(post,editPost));
        }else {
            throw new ResourceNotFoundException("Không có post với slug là " + slug + " trong database");
        }

    }

    public List<PostEntity> posts(){
        return repository.findAll();
    }

    public PostEntity postbyId(long id){
        try {
            return repository.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("Không có post có id là " + id + " trong database");
        }
    }

    public PostEntity postBySlug(String slug){
        try {
            return repository.findBySlug(slug).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("Không có post có slug là " + slug + " trong database");
        }
    }

    @Transactional
    public void delete(long id){
        Optional<PostEntity> post = repository.findById(id);
        if(post.isPresent()){
            PostEntity deletePost = post.get();
            List<Long> listLongCategory = deletePost.getCategories().stream().map(CategoryEntity::getId).collect(Collectors.toList());
            listLongCategory.forEach(deletePost::removeCategory);

            List<Long> listLongTag = deletePost.getTags().stream().map(TagEntity::getId).collect(Collectors.toList());
            listLongTag.forEach(deletePost::removeTag);
            repository.delete(deletePost);
        }else {
            throw  new ResourceNotFoundException("Không có id post " + id + " trong database");
        }
    }

    PostEntity handlePost(PostDTO post, PostEntity newPost){
        post.getCategories().forEach(i -> {
            CategoryEntity category = categoryRepository.findById(i.longValue()).orElse(null);
            if(category != null){
                newPost.addCategory(category);
            }
        });

        post.getTags().forEach(i -> {
            TagEntity tag = tagRepository.findById(i.longValue()).orElse(null);
            if(tag != null){
                newPost.addTag(tag);
            }
        });

        String slug = StringHepler.toSlug(post.getTitle());
        int num = 0;
        while (repository.existsBySlug(slug)){
            slug = post.getTitle() + "_" + num;
            num++;
        }

        UserEntity user = userService.getUserDetail(post.getIdAuthor());

        newPost.setUser(user);
        newPost.setContent(post.getContent());
        newPost.setSlug(slug);
        newPost.setStatus(post.getStatus());
        newPost.setTitle(post.getTitle());
        newPost.setDescription(post.getDescription());
        newPost.setThumbnail(post.getThumbnail());

        return newPost;
    }
}
