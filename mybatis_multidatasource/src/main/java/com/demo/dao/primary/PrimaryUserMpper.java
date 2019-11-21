package com.demo.dao.primary;

import com.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

public interface PrimaryUserMpper{
    List<User> getAll();
}
