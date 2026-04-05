package com.pang.Service;

import com.pang.Pojo.Emp;
import com.pang.Pojo.PageBean;
import java.time.LocalDate;
import java.util.List;


public interface EmpService {

    //分页查询
    PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end);

    //删除员工
    int delete(List<Integer> ids);

    //添加员工
    int save(Emp emp);

    //登录验证
    Emp login(Emp emp);

    //通过id删除员工
    void deleteById(Integer id);
}
