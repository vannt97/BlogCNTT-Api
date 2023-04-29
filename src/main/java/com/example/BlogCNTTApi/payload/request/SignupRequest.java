package com.example.BlogCNTTApi.payload.request;

import javax.persistence.ManyToOne;
import javax.validation.constraints.*;

public class SignupRequest {

    @NotBlank(message = "Name bị bỏ trống")
    private String name;

    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Password bắt buộc phải có")
    private String password;

    @NotBlank(message = "Password Confirm bắt buộc phải có")
    private String confirmPassword;

    @NotNull(message = "Role bắt buộc phải có")
    private int role;

    public SignupRequest() {
    }

    public SignupRequest(String name, String email, String password, String confirmPassword, int role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
