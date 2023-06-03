package com.example.springsecurity.Test;

import com.example.springsecurity.entity.SysUser;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.mapper.SysUserMapper;
import com.example.springsecurity.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class testDemo {
    @Autowired
    UserMapper userMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Test
    public void findByName(){
        User admin = userMapper.findByUserName("admin");
//        List<SysUser> sysUsers = sysUserMapper.selectAll();
        System.out.println(admin);
    }
}
