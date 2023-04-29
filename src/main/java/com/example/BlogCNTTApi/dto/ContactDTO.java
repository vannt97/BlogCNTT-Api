package com.example.BlogCNTTApi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ContactDTO {

    @NotBlank(message = "name không được bỏ trống")
    private String name;

    @NotBlank(message = "email không được bỏ trống")
    @Email
    private String email;

    @NotBlank(message = "subject không được bỏ trống")
    private String subject;

    private String message;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
