package com.pang.Service.impl;

import com.pang.Anno.Log;
import com.pang.Mapper.DeptMapper;
import com.pang.Pojo.Dept;
import com.pang.Pojo.DeptLog;
import com.pang.Service.DeptLogService;
import com.pang.Service.DeptService;
import com.pang.Service.EmpService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper mapper;

    @Resource
    private DeptLogService deptLogService;

    @Resource
    private EmpService empService;

    @Override
    public List<Dept> selectAll() {
        log.info("查询所有的部门");
        return mapper.selectAll();
    }

    @Override
    public Dept selectById(int id) {
        log.info("查询单个部门");
        return mapper.selectById(id);
    }

    @Transactional
    @Override
    public int deleteById(int id) {
        try {

            log.info("通过id删除部门");   //可能这里需要做一下事务管理
            int count = mapper.deleteById(id);
            
            empService.deleteById(id);

            return count;
        } finally {
            DeptLog deptLog = new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("执行了解散部门的操作，此次解散的是" + id + "号部门");
            deptLogService.insert(deptLog);
        }
    }


    @Override
    public int save(Dept dept) {
        log.info("添加员工");
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        int count = mapper.save(dept);
        return count;
    }


    @Override
    public int update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        int count = mapper.update(dept);
        return count;
    }


}
