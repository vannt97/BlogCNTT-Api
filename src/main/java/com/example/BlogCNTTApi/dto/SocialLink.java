package com.example.BlogCNTTApi.dto;

import com.example.BlogCNTTApi.enums.ESocialLink;

public class SocialLink {

    private ESocialLink name;
    private String url;

    public SocialLink() {
    }

    public SocialLink(ESocialLink name, String url) {
        this.name = name;
        this.url = url;
    }

    public ESocialLink getName() {
        return name;
    }

    public void setName(ESocialLink name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
