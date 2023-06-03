package com.example.springsecurity.security;

import com.example.springsecurity.exception.VerificationCodeException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义一个专门用于校验验证码的过滤器
public class VerificationCodeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationFailureHandler authenticationFailureHandler = new failureHandle();
        //对于登录请求才拦截
        String URL = httpServletRequest.getRequestURI();
        if ("/auth/form".equals(URL) && httpServletRequest.getMethod().equals("POST")) {
            try {
                validate(httpServletRequest);
//                filterChain.doFilter(httpServletRequest,httpServletResponse);
            } catch (VerificationCodeException e) {
                // 交给认证失败处理器
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }



    // 校验验证码逻辑
    private void validate(HttpServletRequest httpServletRequest) throws VerificationCodeException {
        // 获取存在session的验证码和用户输入的验证码
        String sessionCaptcha = (String)httpServletRequest.getSession().getAttribute("captcha");
        String pageCaptcha = httpServletRequest.getParameter("captcha");
        if (StringUtils.isEmpty(sessionCaptcha) || StringUtils.isEmpty(pageCaptcha) || !sessionCaptcha.equals(pageCaptcha)) {
            //校验不通过，抛出异常
            throw new VerificationCodeException("验证码错误");
        }
        // 随手清除验证码，不管是失败还是成功，所以客户端应在登录失败时刷新验证码
        httpServletRequest.removeAttribute("captcha");
    }

}
