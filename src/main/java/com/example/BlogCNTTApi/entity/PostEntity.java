package com.example.BlogCNTTApi.entity;

import com.example.BlogCNTTApi.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
public class PostEntity extends BaseEntity implements IPostBuilder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false,unique = true)
    private long id;

    @Column(name = "title")
    private String title;

    private String slug;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    private String thumbnail;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    private long views;

    private long likes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "post_category",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") })
    private Set<CategoryEntity> categories = new HashSet<>();

    public void addCategory(CategoryEntity category){
        this.categories.add(category);
        category.getPosts().add(this);
    }

    public void removeCategory(long categoryId){
        CategoryEntity category = this.categories.stream().filter(t -> t.getId() == categoryId).findFirst().orElse(null);
        if(category != null){
            this.categories.remove(category);
            category.getPosts().remove(this);
        }
    }

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "post_tag",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private Set<TagEntity> tags = new HashSet<>();

    public void addTag(TagEntity tag){
        this.tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(long tagId){
        TagEntity tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
        if(tag != null){
            this.tags.remove(tag);
            tag.getPosts().remove(this);
        }
    }
    public PostEntity() {
    }

    public PostEntity(String title, String slug, String content, EStatus status, String thumbnail,
                      String description, Set<CategoryEntity> categories, Set<TagEntity> tags,
                      UserEntity user) {
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.status = status;
        this.thumbnail = thumbnail;
        this.description = description;
        this.categories = categories;
        this.tags = tags;
        this.user = user;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public IPostBuilder buildTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IPostBuilder buildSlug(String slug) {
        this.slug = slug;
        return this;
    }

    @Override
    public IPostBuilder buildContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public IPostBuilder buildStatus(EStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public IPostBuilder buildDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IPostBuilder buildThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    @Override
    public IPostBuilder buildCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
        return this;
    }

    @Override
    public IPostBuilder buildTags(Set<TagEntity> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public IPostBuilder buildAuthor(UserEntity user) {
        this.user = user;
        return this;
    }

    @Override
    public PostEntity build() {
        return new PostEntity(this.title,this.slug,this.content,this.status,this.thumbnail,this.description, this.categories, this.tags, this.user);
    }
}
