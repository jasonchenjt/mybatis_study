package com.demo;

import com.demo.dao.primary.PrimaryUserMpper;
import com.demo.dao.second.SecondUserMpper;
import com.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisMultidatasourceApplicationTests {

    @Autowired
    PrimaryUserMpper primaryUserMpper;

    @Autowired
    SecondUserMpper secondUserMpper;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testgetAllUser(){
        List<User> userList = primaryUserMpper.getAll();
        List<User> userList1 = secondUserMpper.getAll();
        System.out.println("主数据库:"+userList.size());
        System.out.println("副数据库:"+userList1.size());
    }

}
