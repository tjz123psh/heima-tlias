package com.pang.Service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pang.Mapper.EmpMapper;
import com.pang.Pojo.Emp;
import com.pang.Pojo.PageBean;
import com.pang.Service.EmpService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Resource
    private EmpMapper empMapper;

    //分页查询
    @Override
    public PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end){
        //这里的PageHelper是指向page，查询完结果并类型转换后将自动分页，然后将存在里面的结果：总记录条数，当前页的结果
        PageHelper.startPage(page,pageSize);
        List<Emp> list = empMapper.page(name,gender,begin,end);
        Page<Emp> p = (Page<Emp>) list;
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public int delete(List<Integer> ids) {
        int count = empMapper.delete(ids);
        return count;
    }

    @Override
    public int save(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        int count = empMapper.save(emp);
        return count;
    }

    @Override
    public Emp login(Emp emp) {
        return empMapper.login(emp);
    }

    @Override
    public void deleteById(Integer id) {
        //部门id
        empMapper.deleteById(id);
    }


}
