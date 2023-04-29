package com.example.BlogCNTTApi.controller;

import com.example.BlogCNTTApi.entity.UserEntity;
import com.example.BlogCNTTApi.enums.ESocialLink;
import com.example.BlogCNTTApi.exceptions.InvalidEnumValueException;
import com.example.BlogCNTTApi.payload.request.UserRequest;
import com.example.BlogCNTTApi.payload.response.ResponseData;
import com.example.BlogCNTTApi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.type.EnumType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.EnumUtils;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("users")
    public ResponseEntity<?> getUsers() {
        List<UserEntity> users = userService.getUsers();
        ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),users);
        return new ResponseEntity<Object>(responseData, HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id){
        ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),userService.getUserDetail(id));
        return new ResponseEntity<Object>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        userService.removeUser(id);
        ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),"Xoá thành công");
        return new ResponseEntity<Object>(responseData, HttpStatus.OK);
    }

    @PostMapping("user/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest request) {
        try {
            request.getLinks().forEach(i -> {
                ESocialLink.valueOf(i.getName().name());
            });
        }catch (Exception e){
            throw new InvalidEnumValueException("tên của link trong dữ liệu nhận được không đúng");
        }
        if (request.getPassword().equals(request.getConfirmPassword())) {
            UserEntity user = userService.addUser(request);
            ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),"Tạo thành công");
            return new ResponseEntity<>(responseData,HttpStatus.OK);
        } else {
            ResponseData responseData = new ResponseData(false,HttpStatus.CONFLICT.value(),"Password và confirmPassword không trùng nhau");
            return new ResponseEntity<>(responseData,HttpStatus.OK);
        }
    }

    @PutMapping("user/edit")
    public ResponseEntity<?> editUser(@Valid @RequestBody UserRequest request){

        try {
            request.getLinks().forEach(i -> {
                ESocialLink.valueOf(i.getName().name());
            });
        }catch (Exception e){
            throw new InvalidEnumValueException("tên của link trong dữ liệu nhận được không đúng");
        }

        if (request.getPassword().equals(request.getConfirmPassword())) {
            userService.editUser(request);
            ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),"Edit thành công");
            return new ResponseEntity<>(responseData,HttpStatus.OK);
        } else {
            ResponseData responseData = new ResponseData(false,HttpStatus.CONFLICT.value(),"Password và confirmPassword không trùng nhau");
            return new ResponseEntity<>(responseData,HttpStatus.OK);
        }
    }


//    @PostMapping("account/create")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> createAccount(@RequestBody SignupRequest signupRequest){
//
//        return new ResponseEntity<>("",HttpStatus.OK);
//    }

//    @DeleteMapping("account/{id}/delete")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> deleteAccount(@PathVariable long id){
//        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
//    }

//    @PostMapping("account/edit")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> editAccount(@RequestBody EditAccountDTO editAccount){
//        ResponseData responseData = new ResponseData();
//        if(accountService.editAccount(editAccount)){
//            responseData.setSucces(true);
//            responseData.setStatus(HttpStatus.OK.value());
//            responseData.setData("Edit thành công");
//        }else {
//            responseData.setSucces(false);
//            responseData.setStatus(HttpStatus.OK.value());
//            responseData.setData("Edit thất bại");
//        }
//        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
//    }
}
