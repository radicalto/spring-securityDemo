package com.example.springsecurity.config;

import com.example.springsecurity.security.*;
import com.example.springsecurity.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.remember-me.key}")
    private String rememberKey;
    @Autowired
    successHandle successHandle;
    @Autowired
    failureHandle failureHandle;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    DataSource dataSource;
    @Autowired
    SignOutHandler signOutHandler;
//    @Autowired
//    SpringSessionBackedSessionRegistry redisSessionRegistry;
    // 自定义认证实现图形验证码验证
//    @Autowired
//    MyWebAuthenticationDetailsSource myWebAuthenticationDetailsSource;
//
//    @Autowired
//    MyAuthenticationProvider myAuthenticationProvider;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //应用AuthenticationProvider
//        auth.authenticationProvider(myAuthenticationProvider);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("123").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        DataSource dataSource = new JdbcConfig().dataSource();
        jdbcTokenRepository.setDataSource(dataSource);

        http.cors().and().csrf().disable()
                // 返回了一个URL拦截注册器
                .authorizeRequests()
                // ANT模式的URL匹配器 如 "/admin/api/**" 匹配/admin/api/下面所有的API
                .antMatchers("/index.html").permitAll()
//                .antMatchers("/admin/api/**").hasRole("ADMIN")
//                .antMatchers("/admin/api/**").hasRole("USER")
//                .antMatchers("/admin/api/**").permitAll()
//                //开放/captcha.jpg的访问权限
                .antMatchers("/app/api/**","/captcha").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .failureHandler(failureHandle)
                .successHandler(successHandle)

                //应用AuthenticationDetailsSource
//                .authenticationDetailsSource(myWebAuthenticationDetailsSource)

                // 自定义页面
                .loginPage("/index.html")
                // 指定处理登录请求的路径, 如果不设置就默认为文件名 如:/index.html
                .loginProcessingUrl("/auth/form").permitAll()
                .and().rememberMe()
                .userDetailsService(myUserDetailsService)
                // 增加持久化令牌 (使用security自带的jdbc方案)
                .tokenRepository(jdbcTokenRepository)
                // 1. 散列加密方案
//                .key("blurool")
                // 设置key
                .key(rememberKey)
                //设置令牌有效期，为7天有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                // 退出登入
                .and()
                .logout()
                .addLogoutHandler(signOutHandler)
//                //可自己设置
//                //指定接收注销请求的路由
//                .logoutUrl("/index.html")
//                // 注销成功，重定向到该路径下
//                .logoutSuccessUrl("/")
//                //使该用户的HttpSession失效
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID","remember-me")

                // 配置自定义的过滤器
                .and()
                .addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class)

                // 会话管理配置器
                .sessionManagement()
                /**
                 * 防止会话固定攻击(通过URL+sessionId进行登入)
                 * 有四种防止策略
                 * none: 不做变动，仍沿用旧的session
                 * newSession: 登入后创建于一个新的session
                 * migrateSession: (默认方式)登入后创建于一个新的session,并将旧的session数据复制过来
                 * changeSession: 不创建新的会话,使用servlet容器提供的会话固定保护
                 */
                .sessionFixation().migrateSession()
                // 设置最大并发数，超过会踢掉旧连接
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true) // 阻止新的连接，而不是踢掉旧连接
                // 使用session提供的会话注册表
//                .sessionRegistry(redisSessionRegistry)
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
