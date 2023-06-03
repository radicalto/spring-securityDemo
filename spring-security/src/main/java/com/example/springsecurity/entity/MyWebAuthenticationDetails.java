package com.example.springsecurity.entity;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    String sessionCaptcha;

    String imageCaptcha;

    public String getSessionCaptcha() {
        return sessionCaptcha;
    }

    public String getImageCaptcha() {
        return imageCaptcha;
    }

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);  //验证的逻辑不变
        this.imageCaptcha = request.getParameter("captcha");
        HttpSession session = request.getSession();
        this.sessionCaptcha = (String) session.getAttribute("captcha");
        // 随手清除验证码，不管是失败还是成功，所以客户端应在登录失败时刷新验证码
        if (!StringUtils.isEmpty(sessionCaptcha)){
            session.removeAttribute("captcha");
        }
    }
}
