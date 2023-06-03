package com.example.springsecurity.Test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class passwordEncoderTestDemo {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void encryption(){
        String encode = passwordEncoder.encode("123");
        System.out.println("encode: "+encode);
        boolean matches = passwordEncoder.matches("123", encode);
        System.out.println("matches: "+matches);
    }

}
