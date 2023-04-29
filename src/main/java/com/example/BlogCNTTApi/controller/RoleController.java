package com.example.BlogCNTTApi.controller;

import com.example.BlogCNTTApi.payload.response.ResponseData;
import com.example.BlogCNTTApi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("roles/create")
    public ResponseEntity<?> addRoleAuto(){
        roleService.createDataRoleAuto();
        ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(), "Tạo thành công role");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("roles")
    public ResponseEntity<?> getRoles(){
        ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(), roleService.getRoles());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
