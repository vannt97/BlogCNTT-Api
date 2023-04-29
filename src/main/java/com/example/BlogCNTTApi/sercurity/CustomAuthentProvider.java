package com.example.BlogCNTTApi.sercurity;

import com.example.BlogCNTTApi.dto.UserDetailsImpl;
import com.example.BlogCNTTApi.entity.UserEntity;
import com.example.BlogCNTTApi.repository.UserRepository;
import com.example.BlogCNTTApi.service.UserDetailsServiceImpl;
import com.example.BlogCNTTApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthentProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = userDetailsService.loadUserByUsername(email);

        if(webSecurityConfig.passwordEncoder().matches(password,user.getPassword())){
            return new UsernamePasswordAuthenticationToken(email,password, user.getAuthorities());
        }else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
