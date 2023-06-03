package com.example.springsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.example.springsecurity.mapper")
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    // 引入多用户支持
    /*
    1. InMemoryUserDetailsManager
     */
//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        //创建admin，角色为admin
//        inMemoryUserDetailsManager.createUser(User.withUsername("admin").password("admin").roles("ADMIN").build());
//        //创建user用户，角色为user
//        inMemoryUserDetailsManager.createUser(User.withUsername("user").password("user").roles("USER").build());//返回
//        return inMemoryUserDetailsManager;
//    }

}
