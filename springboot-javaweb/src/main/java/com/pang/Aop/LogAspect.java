package com.pang.Aop;

import com.alibaba.fastjson.JSONObject;
import com.pang.Mapper.OperateLogMapper;
import com.pang.Pojo.OperateLog;
import com.pang.Utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;


@Slf4j
@Component
@Aspect
public class LogAspect {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private OperateLogMapper operateLogMapper;

    //切点表达式（通过注解的方式将为方法提供AOP）
    @Around("@annotation(com.pang.Anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //1.获取操作人id
        String jwt = httpServletRequest.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer id = (Integer) claims.get("id");

        //2.获取操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //3.获取操作类名
        String className = joinPoint.getSignature().getClass().getName();

        //4.获取操作方法名
        String methodName = joinPoint.getSignature().getName();

        //5.获取操作的方法参数
        Object[] args = joinPoint.getArgs();
        String methodParam = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        //6.获取方法返回值和耗时
        Object Result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        String returnValue = JSONObject.toJSONString(Result);
        long costTime = end - begin;

        OperateLog operateLog = new OperateLog(null,id,operateTime,className,methodName,methodParam,returnValue,costTime);
        operateLogMapper.insert(operateLog);

        log.info("实现了切面");
        return Result;
    }
}
