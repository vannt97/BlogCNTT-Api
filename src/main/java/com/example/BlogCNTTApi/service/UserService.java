package com.example.BlogCNTTApi.service;

import com.example.BlogCNTTApi.dto.UserDetailsImpl;
import com.example.BlogCNTTApi.entity.RoleEntity;
import com.example.BlogCNTTApi.entity.UserDetailEntity;
import com.example.BlogCNTTApi.entity.UserEntity;
import com.example.BlogCNTTApi.entity.UserSocialConnectEntity;
import com.example.BlogCNTTApi.exceptions.AccountAlreadyExistsException;
import com.example.BlogCNTTApi.exceptions.ResourceNotFoundException;
import com.example.BlogCNTTApi.payload.request.SignupRequest;
import com.example.BlogCNTTApi.payload.request.UserRequest;
import com.example.BlogCNTTApi.repository.RoleRepository;
import com.example.BlogCNTTApi.repository.UserRepository;
import com.example.BlogCNTTApi.repository.UserSocialConnectRepository;
import com.example.BlogCNTTApi.sercurity.WebSecurityConfig;
import com.example.BlogCNTTApi.util.StringHepler;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Autowired
    UserSocialConnectRepository userSocialConnectRepository;


    public UserEntity getUserDetail(long id){
        Optional<UserEntity> user = userRepo.findById(id);
        if(user.isPresent()){
            return user.get();
        }else {
            throw new ResourceNotFoundException("Không có user id " + id + " trong dữ liệu");
        }
    }

    public List<UserEntity> getUsers(){
        return userRepo.findAll();
    }

    public void removeUser(long id){
        try{
            userRepo.deleteById(id);
        }catch (Exception e){
            throw new ResourceNotFoundException("Không có user id " + id + " trong dữ liệu");
        }
    }

    @Transactional
    public UserEntity addUser(UserRequest request){
        return this.handleAddUser(request);
    }

    @Transactional
    public void editUser(UserRequest request){
        this.handleEditUser(request);
    }

    private UserEntity handleAddUser(UserRequest request){
        Optional<UserEntity> user = userRepo.findByEmail(request.getEmail());
        if(user.isEmpty()){
            UserEntity newUser = new UserEntity();
            newUser.setName(request.getName());
            newUser.setEmail(request.getEmail());
            newUser.setPassword(webSecurityConfig.passwordEncoder().encode(request.getPassword()));
            newUser.setCreatedAt(new Date());
            handleUser(request, newUser);
//            set user detail
            UserDetailEntity newUserDetail = new UserDetailEntity();
            newUserDetail.setUser(newUser);
            newUserDetail.setAvatar(request.getAvatar());
            newUserDetail.setDescription(request.getDescription());
            newUserDetail.setCreatedAt(new Date());
            newUserDetail.setModifiedAt(new Date());
            newUser.setUserDetail(newUserDetail);

//            set Set user social connect
            request.getLinks().forEach(i -> {
                UserSocialConnectEntity newUserSocialConnect = new UserSocialConnectEntity();
                newUserSocialConnect.setIdentifierName(i.getName().name());
                newUserSocialConnect.setIdentifierUrl(i.getUrl());
                newUserSocialConnect.setCreatedAt(new Date());
                newUserSocialConnect.setModifiedAt(new Date());
                newUser.addUserSocialConnects(newUserSocialConnect);
            });

            userRepo.save(newUser);
            return newUser;
        }else {
            throw new AccountAlreadyExistsException("Tài khoản đã tồn tại");
        }
    }

    private void handleUser(UserRequest request, UserEntity newUser) {
        newUser.setModifiedAt(new Date());
        String slug = StringHepler.toSlug(request.getName());
        int num = 0;
        while (userRepo.existsBySlug(slug)){
            slug = request.getName() + "_" + num;
            num++;
        }
        newUser.setSlug(slug);
        Optional<RoleEntity> role = roleRepo.findById(request.getRole());
        if(role.isEmpty()){
            throw new ResourceNotFoundException("Không có role id " + request.getRole() + " trong dữ liệu");
        }
        role.ifPresent(newUser::setRole);
    }

    private void handleEditUser(UserRequest request){
        Optional<UserEntity> user = userRepo.findByEmail(request.getEmail());
        if(user.isPresent()){
            UserEntity userEdit = user.get();
            userEdit.setName(request.getName());
            userEdit.setEmail(request.getEmail());
            userEdit.setPassword(webSecurityConfig.passwordEncoder().encode(request.getPassword()));
            handleUser(request, userEdit);

//            set user detail
            UserDetailEntity userDetailEdit = userEdit.getUserDetail();
            userDetailEdit.setAvatar(request.getAvatar());
            userDetailEdit.setDescription(request.getDescription());
            userDetailEdit.setModifiedAt(new Date());


//            set User social connects
            Set<UserSocialConnectEntity> listDeleteSocialLinks = userEdit.removeAllUserSocialConnects();
            for (UserSocialConnectEntity i:listDeleteSocialLinks){
                userSocialConnectRepository.delete(i);
            }

            request.getLinks().forEach(i -> {
                UserSocialConnectEntity editUserSocialConnect = new UserSocialConnectEntity();
                editUserSocialConnect.setIdentifierName(i.getName().name());
                editUserSocialConnect.setIdentifierUrl(i.getUrl());
                editUserSocialConnect.setModifiedAt(new Date());
                editUserSocialConnect.setCreatedAt(new Date());
                userEdit.addUserSocialConnects(editUserSocialConnect);
            });

            userRepo.save(userEdit);
        }else {
            throw new ResourceNotFoundException("Không có tài khoàn email " + request.getEmail() + " trong database");
        }
    }
}
