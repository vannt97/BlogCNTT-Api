package com.example.BlogCNTTApi.payload.request;


import com.example.BlogCNTTApi.dto.SocialLink;
import com.example.BlogCNTTApi.entity.UserSocialConnectEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserRequest extends SignupRequest{
    private String description = null;
    private String avatar = null;

    private List<SocialLink> links = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<SocialLink> getLinks() {
        return links;
    }

    public void setLinks(List<SocialLink> links) {
        this.links = links;
    }
}
