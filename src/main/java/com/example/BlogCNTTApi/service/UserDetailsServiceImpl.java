package com.example.BlogCNTTApi.service;

import com.example.BlogCNTTApi.dto.UserDetailsImpl;
import com.example.BlogCNTTApi.entity.UserEntity;
import com.example.BlogCNTTApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userCustomService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepo.findByEmail(email);

        if(user.isPresent()){
            return UserDetailsImpl.build(user.get());
        }else{
            return (UserDetails) new UsernameNotFoundException("User Not Found with email: " + email);
        }
    }
}
