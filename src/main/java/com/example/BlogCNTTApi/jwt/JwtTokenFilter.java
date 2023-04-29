package com.example.BlogCNTTApi.jwt;

import com.example.BlogCNTTApi.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    lọc ở phần Header có phần Authorization sẽ có bên front end gửi Bearer token thì tách Bearer ra rồi lấy token
    private String getTokenFromHeader(HttpServletRequest request){
        String strToken = request.getHeader("Authorization");
        if(StringUtils.hasText(strToken) && strToken.startsWith("Bearer ")){
            return strToken.substring(7);
        }else {
            return null;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        if(token != null){
            if(jwtTokenHelper.validateToken(token)){
                String tokenDecode = jwtTokenHelper.decodeToken(token);
//                chuyển cái data mình có lưu được trong token rồi parse nó ra thành hash
                Map<String,String> result = new ObjectMapper().readValue(tokenDecode, HashMap.class);
//                check lại lần nữa bên database xem cái data trong token này có trùng hay ko lỡ là cái giả mạo thì sao
//                ví dụ ở đây mình get dữ liệu email
                UserDetails userDetails = userDetailsService.loadUserByUsername(result.get("email"));
//                check loại token nữa nếu là refesh token thì loại vì không được dùng cái này
//                với lại refeshtoken dùng để tạo mới và nó có thời gian lâu nên không thể dùng được

                if(StringUtils.hasText(result.get("typeToken")) && !result.get("typeToken").equals(TypeToken.RESFESH_TOKEN.name())){

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(),"",userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContext sc = SecurityContextHolder.getContext();
                    sc.setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
