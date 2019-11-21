package com.didispace.domain;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM student WHERE name = #{name}")
    User findByName(@Param("name") String name);

    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    @Select("SELECT name, age FROM student")
    List<User> findAll();

    @Insert("INSERT INTO student(name, age) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    @Update("UPDATE student SET age=#{age} WHERE name=#{name}")
    void update(User user);

    @Delete("DELETE FROM student WHERE id =#{id}")
    void delete(int id);

    @Insert("INSERT INTO student(name, age) VALUES(#{name}, #{age})")
    int insertByUser(User user);

    @Insert("INSERT INTO student(name, age) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Select("SELECT * FROM student WHERE id = #{id}")
    @Results({
            @Result(property = "pet",column = "pid",one = @One(select = "com.didispace.domain.PetMapper.findById"))
    })
    User findAndOne(@Param("id") int id);

}