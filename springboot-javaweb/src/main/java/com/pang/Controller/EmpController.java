package com.pang.Controller;

import com.pang.Anno.Log;
import com.pang.Pojo.Emp;
import com.pang.Pojo.PageBean;
import com.pang.Pojo.Result;
import com.pang.Service.EmpService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {


    @Resource
    private EmpService empService;
    @GetMapping
    public  Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize,
                          String name, Short gender, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("分页查询");

        PageBean page1 = empService.page(page,pageSize,name,gender,begin,end);
        return Result.success(page1);
    }

    @Log
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        int count = empService.delete(ids);
        log.info("删除员工记录数:{}",count);
        return Result.success();
    }

    //添加员工
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp){
        int count = empService.save(emp);
        return Result.success();
    }
}
