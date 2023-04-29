package com.example.BlogCNTTApi.entity;

import com.example.BlogCNTTApi.enums.ESocialLink;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Entity(name = "users")
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false,unique = true)
    private long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 150, updatable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "remember_token", columnDefinition = "TEXT")
    private String rememberToken;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserDetailEntity userDetail;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<UserSocialConnectEntity> userSocialConnects = new HashSet<UserSocialConnectEntity>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<PostEntity> posts;

    public Set<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostEntity> posts) {
        this.posts = posts;
    }

    public Set<UserSocialConnectEntity> getUserSocialConnects() {
        return userSocialConnects;
    }

    public void addUserSocialConnects(UserSocialConnectEntity userSocialConnect){
        this.userSocialConnects.add(userSocialConnect);
        userSocialConnect.setUser(this);
    }

    public UserSocialConnectEntity removeUserSocialConnects(int index){
//        this.userSocialConnects
        List<UserSocialConnectEntity> list = new ArrayList<UserSocialConnectEntity>(this.userSocialConnects);
//        UserSocialConnectEntity userSocialConnect =  this.userSocialConnects.stream().filter(t -> t.getIdentifierName().equals("TIKTOK")).findFirst().orElse(null);
        this.userSocialConnects.remove(list.get(index));
        return list.get(index);
    }

    public Set<UserSocialConnectEntity> removeAllUserSocialConnects(){
        Set<UserSocialConnectEntity> listEdit = new HashSet<>();
        for (UserSocialConnectEntity i : this.userSocialConnects){
            listEdit.add(i);
        }
        this.userSocialConnects.removeAll(listEdit);
        return listEdit;
    }


    public void setUserSocialConnects(Set<UserSocialConnectEntity> userSocialConnects) {
        this.userSocialConnects = userSocialConnects;
    }

    public UserEntity() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public UserDetailEntity getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailEntity userDetail) {
        this.userDetail = userDetail;
    }

    //    @ManyToOne
//    @JoinColumn(name="role_id", nullable=false)
//    private RoleEntity role;
//
//    public RoleEntity getRole() {
//        return role;
//    }
//
//    public void setRole(RoleEntity role) {
//        this.role = role;
//    }
//
//    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnore
//    private Set<BlogEntity> blogs;
//
//    public Set<BlogEntity> getBlogs() {
//        return blogs;
//    }
//    public void setBlogs(Set<BlogEntity> blogs) {
//        this.blogs = blogs;
//    }



}
