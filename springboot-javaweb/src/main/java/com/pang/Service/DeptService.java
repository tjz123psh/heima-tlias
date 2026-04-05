package com.pang.Service;


import com.pang.Pojo.Dept;

import java.util.List;

public interface DeptService {
    //查看所有的部门
    List<Dept> selectAll();
    //通过id查询部门
    Dept selectById(int id);
    //通过id删除
    int deleteById(int id);
    //添加部门
    int save(Dept dept);
    //修改部门
    int update(Dept dept);

}
