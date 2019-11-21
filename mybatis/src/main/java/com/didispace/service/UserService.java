package com.didispace.service;

import com.didispace.domain.User;
import com.didispace.domain.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Cacheable(value = "username",key = "#name")
    public User finduserbyname(String name ){
        User user= userMapper.findByName("Jason");
        System.out.println("查询出来的User:"+user);
        return user;
    }

    public User findByName(String name){
        User user = userMapper.findByName(name);
        return user;
    }

    public List<User>findAllUser(){
        List<User> userList = userMapper.findAll();
        return userList;
    }
}
