package com.pang.Controller;

import com.pang.Pojo.Emp;
import com.pang.Pojo.Result;
import com.pang.Service.EmpService;
import com.pang.Utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource                                  
    private EmpService service;

    @PostMapping
    public Result jwt(@RequestBody Emp emp){
        Emp e = service.login(emp);

        if(e != null){
            Map<String,Object> map = new HashMap<>();
            map.put("id",e.getId());
            map.put("username",e.getUsername());
            map.put("password",e.getPassword());

            String jwt = JwtUtils.generateJwt(map);
            return Result.success(jwt);
        }

        return Result.failure("NOT_LOGIN");
    }
}
