package com.xc.springsecurity02.Test;

import com.xc.springsecurity02.mapper.CustomerMapper;
import com.xc.springsecurity02.mapper.UserMapper;
import com.xc.springsecurity02.pojo.Customer;
import com.xc.springsecurity02.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class testDemo {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    UserMapper userMapper;
    @Test
    public void selectAll(){
        List<User> customers = userMapper.selectAll();
        for (User user : customers) {
            System.out.println(user);
        }

    }
}
