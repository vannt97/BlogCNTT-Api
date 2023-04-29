package com.example.BlogCNTTApi.dto;

import javax.validation.constraints.NotBlank;

public class VisitorDTO {

    @NotBlank
    private String ipAddress;
    @NotBlank
    private String userAgent;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
