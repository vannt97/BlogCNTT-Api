package com.example.BlogCNTTApi.controller;

import com.example.BlogCNTTApi.dto.UserDetailsImpl;
import com.example.BlogCNTTApi.entity.UserEntity;
import com.example.BlogCNTTApi.jwt.JwtTokenHelper;
import com.example.BlogCNTTApi.jwt.TypeToken;
import com.example.BlogCNTTApi.payload.request.SigninRequest;
import com.example.BlogCNTTApi.payload.request.SignupRequest;
import com.example.BlogCNTTApi.payload.request.UserRequest;
import com.example.BlogCNTTApi.payload.response.ResponseData;
import com.example.BlogCNTTApi.service.RoleService;
import com.example.BlogCNTTApi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginControler {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenHelper tokenHelper;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("signup")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        if (signupRequest.getPassword().equals(signupRequest.getConfirmPassword())){

//            set user request
            UserRequest userRequest = new UserRequest();
            userRequest.setEmail(signupRequest.getEmail());
            userRequest.setName(signupRequest.getName());
            userRequest.setPassword(signupRequest.getPassword());
            userRequest.setConfirmPassword(signupRequest.getConfirmPassword());
            userRequest.setRole(signupRequest.getRole());

            UserEntity user = userService.addUser(userRequest);
            UserDetailsImpl userDetails = UserDetailsImpl.build(user);
            Map<String,Object> data = this.createDataInResponseData(userDetails);
            ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),data);
            return new ResponseEntity<>(responseData,HttpStatus.OK);
        }else {
            ResponseData responseData = new ResponseData(false,HttpStatus.CONFLICT.value(),"Password và confirmPassword không trùng nhau");
            return new ResponseEntity<>(responseData,HttpStatus.CONFLICT);
        }
    }

    @PostMapping("signin")
    public ResponseEntity<?> signin(
            @Valid @RequestBody SigninRequest signinRequest) throws JsonProcessingException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Map<String,Object> data = this.createDataInResponseData(userDetails);
        ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),data);
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    private Map<String, Object> createDataInResponseData(UserDetailsImpl userDetails) throws JsonProcessingException {
        Map<String,Object> data = new HashMap<>();
        data.put("name", userDetails.getName());
        data.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        data.put("email",userDetails.getEmail());
//      gia han 12 tieng
        String token = this.createToken(TypeToken.TOKEN,userDetails,12 * 60 * 60 * 1000);
//        gia han khac chua tinh
        String refeshToken = this.createToken(TypeToken.RESFESH_TOKEN,userDetails,30 * 60 * 60 * 1000);
        data.put("token",token);
        data.put("refreshToken",refeshToken);
        return data;
    }

    private String createToken(TypeToken typeToken, UserDetailsImpl user, long expiredDate) throws JsonProcessingException {
        Map<String, Object> dataToken = new HashMap<>();
        dataToken.put("name", user.getName());
        dataToken.put("role", user.getAuthorities().iterator().next().getAuthority());
        dataToken.put("email",user.getEmail());
        dataToken.put("typeToken",typeToken);
        return tokenHelper.generateToken((new ObjectMapper()).writeValueAsString(dataToken), expiredDate);
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
