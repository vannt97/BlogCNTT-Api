package com.example.BlogCNTTApi.controller;

import com.example.BlogCNTTApi.dto.PostDTO;
import com.example.BlogCNTTApi.payload.response.ResponseData;
import com.example.BlogCNTTApi.service.PostService;
import com.example.BlogCNTTApi.util.StringHepler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@RestController
public class PostController {

    @Autowired
    PostService service;

    @GetMapping("posts")
    public ResponseEntity<?> datas(){
        ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),service.posts());
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    @GetMapping("posts/{param}")
    public ResponseEntity<?> data(@PathVariable String param){
        ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),"");

        if(StringHepler.isNumeric(param)){
            responseData.setData(service.postbyId(Long.parseLong(param)));
        }else {
            responseData.setData(service.postBySlug(param));
        }
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    @PostMapping("post/create")
    public ResponseEntity<?> create(@Valid @RequestBody PostDTO post){
        service.create(post);
        ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),"Tạo thành công");
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    @PutMapping("post/edit")
    public ResponseEntity<?> edit(@RequestBody PostDTO post , HttpServletRequest request){
        String slug = request.getHeader("slug");
        try {
            service.edit(post,slug);
            ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(), "Edit thành công");
            return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
        }catch (Exception e){
            ResponseData responseData = new ResponseData(false, HttpStatus.CONFLICT.value(), "không có slug trong header");
            return new ResponseEntity<ResponseData>(responseData, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("post/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        service.delete(id);
        ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(), "Xoá thành công");
        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
    }
}
