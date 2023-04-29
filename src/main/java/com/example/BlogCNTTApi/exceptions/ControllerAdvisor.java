package com.example.BlogCNTTApi.exceptions;

import com.example.BlogCNTTApi.payload.response.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ResponseData> handleAccountAlreadyExistsException(RuntimeException ex, WebRequest request){
        ResponseData responseData = new ResponseData(false,HttpStatus.CONFLICT.value(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseData);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseData> handleResourceNotFoundException(RuntimeException ex, WebRequest request){
        ResponseData responseData = new ResponseData(false,HttpStatus.CONFLICT.value(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseData);
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<ResponseData> handleInvalidEnumValueException(RuntimeException ex, WebRequest request){
        ResponseData responseData = new ResponseData(false,HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseData responseData = new ResponseData(false,status.value(),"Dữ liệu bạn truyền vào bị không đúng cấu trúc đã quy định trong Enum");
        return ResponseEntity.status(status).body(responseData);
    }
}
