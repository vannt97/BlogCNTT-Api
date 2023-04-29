package com.example.BlogCNTTApi.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class SigninRequest {

    @NotBlank(message = "Email bắt buộc phải có")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Password bắt buộc phải có")
    private String password;

    public SigninRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SigninRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
