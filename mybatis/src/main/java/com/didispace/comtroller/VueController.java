package com.didispace.comtroller;

import com.didispace.domain.User;
import com.didispace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VueController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getOneUser")
    public User returnOneUser(String name){
        System.out.println("获取页面中的name:"+name);
        User user = userService.findByName(name);
        return user;
    }

    @RequestMapping("/getAllUser")
    public List<User> returnAllUser(String name){
        List<User> userList = userService.findAllUser();
        return userList;
    }
}
