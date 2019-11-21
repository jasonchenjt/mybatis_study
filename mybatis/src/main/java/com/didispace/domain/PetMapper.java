package com.didispace.domain;

import org.apache.ibatis.annotations.*;

@Mapper
public interface PetMapper {

    @Select("Select * from pet where id = #{id}")
    @Results({
            @Result(property = "name",column = "PNAME")
    })
    Pet findById(@Param("id") int id);
}
