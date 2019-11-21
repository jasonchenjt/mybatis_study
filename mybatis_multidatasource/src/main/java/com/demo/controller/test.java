package com.demo.controller;

import com.demo.dao.primary.PrimaryUserMpper;
import com.demo.dao.second.SecondUserMpper;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class test {

    @Autowired
    private PrimaryUserMpper primaryUserMpper;

    @Autowired
    private SecondUserMpper secondUserMpper;

    @RequestMapping("/primary")
    public String primary(){
        List<User> userList = primaryUserMpper.getAll();
        System.out.println("userList的长度:"+userList.size());
        return "success";
    }

    @RequestMapping("/second")
    public String second(){
        List<User> userList = secondUserMpper.getAll();
        System.out.println("userList的长度:"+userList.size());
        return "success";
    }

}
