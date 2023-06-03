package com.example.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;


public class JdbcUserApplication {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(JdbcUserApplication.class, args);
    }

    // 引入多用户支持
    /*
        1. JdbcUserDetailsManager
     */

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        /*
            由于每次启动都会重新创建，而username为主键，所以因再次创建报错
         */
        if (!jdbcUserDetailsManager.userExists("admin")){
            jdbcUserDetailsManager.createUser(User.withUsername("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("ADMIN").build());
        }
        if (!jdbcUserDetailsManager.userExists("user")) {
            jdbcUserDetailsManager.createUser(User.withUsername("user").password(new BCryptPasswordEncoder().encode("user")).roles("USER").build());
        }
        return jdbcUserDetailsManager;
    }
}
