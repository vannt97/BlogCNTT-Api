package com.example.BlogCNTTApi.controller;

import com.example.BlogCNTTApi.payload.response.ResponseData;
import com.example.BlogCNTTApi.repository.CategoryRepository;
import com.example.BlogCNTTApi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping("categories")
    public ResponseEntity<?> categories(){
        ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(), service.categories());
        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
    }

    @PostMapping("category/create")
    public ResponseEntity<?> create(@RequestParam String name){
        try {
            service.create(name);
            ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(), "Tạo thành công");
            return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
        }catch (Exception e){
            ResponseData responseData = new ResponseData(false, HttpStatus.CONFLICT.value(), "name trong database bị trùng");
            return new ResponseEntity<ResponseData>(responseData, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("category/edit")
    public ResponseEntity<?> edit(@RequestParam String name , @RequestParam long id){
        try {
            service.edit(id,name);
            ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(), "Edit thành công");
            return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
        }catch (Exception e){
            ResponseData responseData = new ResponseData(false, HttpStatus.CONFLICT.value(), "name trong database bị trùng");
            return new ResponseEntity<ResponseData>(responseData, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("category/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        service.delete(id);
        ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(), "Xoá thành công");
        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
    }
}
