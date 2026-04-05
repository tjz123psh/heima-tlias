package com.pang.Mapper;

import com.pang.Pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("select * from dept")
    List<Dept> selectAll();

    @Delete("delete from dept where id = #{id}")
    int deleteById(int id);

    @Insert("insert into dept (name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    int save(Dept dept);

    @Select("select * from dept where id = #{id}")
    Dept selectById(int id);

    int update(Dept dept);
}
