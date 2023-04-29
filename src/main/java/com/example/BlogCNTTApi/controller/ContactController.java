package com.example.BlogCNTTApi.controller;

import com.example.BlogCNTTApi.dto.ContactDTO;
import com.example.BlogCNTTApi.payload.response.ResponseData;
import com.example.BlogCNTTApi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping("contact")
    public ResponseEntity<?> postContact(@RequestBody @Valid ContactDTO contactRequest){
        contactService.createContact(contactRequest);
        ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(), "Gửi Contact thành công");
        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResponseData responseData = new ResponseData(false,HttpStatus.BAD_REQUEST.value(), errors);
        return new ResponseEntity<ResponseData>(responseData,HttpStatus.BAD_REQUEST);
    }
}
