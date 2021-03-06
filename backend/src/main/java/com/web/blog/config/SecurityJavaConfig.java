package com.web.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()		//cors방지
                .csrf().disable()		//csrf방지
                .formLogin().disable()	//기본 로그인 페이지 없애기
                .headers().frameOptions().disable();	
    }
    
    //Bycrypt 를 사용한 PasswordEncoder bean을 생성
    @Bean
    //bean을 생성하면 @Autowired를 통해 PasswordEncoder를 선언할때 자동으로 클래스가 바인딩 된다.
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}