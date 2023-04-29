package com.example.BlogCNTTApi.entity;

import com.example.BlogCNTTApi.enums.EStatus;
import java.util.List;
import java.util.Set;

public interface IPostBuilder {
    IPostBuilder buildTitle(String title);
    IPostBuilder buildSlug(String slug);
    IPostBuilder buildContent(String content);
    IPostBuilder buildStatus(EStatus status);
    IPostBuilder buildDescription(String description);
    IPostBuilder buildThumbnail(String thumbnail);
    IPostBuilder buildCategories(Set<CategoryEntity> categories);
    IPostBuilder buildTags(Set<TagEntity> tags);
    IPostBuilder buildAuthor(UserEntity user);
    PostEntity build();
}
