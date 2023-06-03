package com.example.springsecurity.security;

import com.example.springsecurity.entity.MyWebAuthenticationDetails;
import com.example.springsecurity.exception.VerificationCodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

//@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {
    //把构造方法注入UserDetailsService和PasswordEncoder
    public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //这个我们在MyWebAuthenticationDetailsSource里面设置的，所以这里能够拿到
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        String imageCaptcha = details.getImageCaptcha();
        String sessionCaptcha = details.getSessionCaptcha();
        //验证码校验失败，则抛异常
        if (StringUtils.isEmpty(imageCaptcha) || StringUtils.isEmpty(sessionCaptcha) || !imageCaptcha.equals(sessionCaptcha)){
            throw new VerificationCodeException("图形验证码验证失败");
        }

        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
