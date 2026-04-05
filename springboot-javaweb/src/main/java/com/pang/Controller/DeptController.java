package com.pang.Controller;


import com.pang.Anno.Log;
import com.pang.Pojo.Dept;
import com.pang.Pojo.Result;
import com.pang.Service.DeptService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Resource
    private DeptService deptService;

    @GetMapping
    public Result selectAll(){
        log.info("查询所有的部门");
        List<Dept> deptPeople = deptService.selectAll();
        return Result.success(deptPeople);
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable int id){
        Dept dept = deptService.selectById(id);
       return Result.success(dept);
    }


    @Log
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable int id){
        int numberOfAffectedItems = deptService.deleteById(id);
        log.info("删除操作,影响行数:" + numberOfAffectedItems);
        return Result.success();
    }

    @Log
    @PostMapping
    public Result save(@RequestBody Dept dept){
        int count = deptService.save(dept);
        log.info("添加部门:{}", count);
        return Result.success();
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
        int count = deptService.update(dept);
        log.info("修改部门记录数:{}",count);
        return Result.success();
    }
}
