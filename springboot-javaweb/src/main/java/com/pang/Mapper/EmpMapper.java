package com.pang.Mapper;

import com.pang.Pojo.Emp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

     //分页查询
    List<Emp> page(String name, Short gender, LocalDate begin, LocalDate end);

    //删除操作
    int delete(List<Integer> ids);

    //添加
    @Insert("insert into emp (username,name,gender,image,job,entrydate,dept_id,create_time,update_time) values " +
            "(#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    int save(Emp emp);

    //登录验证，看其返回的是一个对象，还是一个null
    @Select("select * from emp where username = #{username} && password = #{password}")
    Emp login(Emp emp);

    @Delete("delete from emp where dept_id = #{deptId}")
    void deleteById(Integer id);
}
