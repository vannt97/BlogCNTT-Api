package com.example.BlogCNTTApi.sercurity;

import com.example.BlogCNTTApi.jwt.JwtAuthenticationEntryPoint;
import com.example.BlogCNTTApi.jwt.JwtTokenFilter;
import com.example.BlogCNTTApi.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig {

    @Resource(name = "userCustomService")
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Bean
    public void InitRootFolder(){
        try {
            Files.createDirectories(Paths.get("uploads"));
        }catch (Exception e){
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
//                chèn expection khi dùng token get vào các controller nó cần quyền thì phải như thế.
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                do mình làm cái api nên không cần phải lưu session cái này dùng để biết user xác minh
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/signup","/signin","/users","/user/*","/roles/create","/files/*","/download-all-image-from-server"
                ,"/contact","/visitor","/categories","/tags","/posts/*","/posts").permitAll()
                .and().authorizeRequests().antMatchers("/upload","/user/create","/user/edit","/user/delete/*").authenticated()
                .anyRequest().authenticated();

//        set up phần authenticationProvider nếu để không thì cái phần mình custom userdetail của spring sẽ không thành công
//      nếu không có cái này thì cái role của mình sẽ vô dụng
        http.authenticationProvider(authenticationProvider());

//        cái này add vào để xác mình khi tiến vào controller
//        theo mình biết sẽ có nhiều filter lọc lắm nên khi muốn kiểm tra nhiều bước thì phải tìm hiểu thêm
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
