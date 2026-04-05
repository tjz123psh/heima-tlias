package com.pang.Exception;


import com.pang.Pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//定义了一个全局异常处理器
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public Result ex(Exception exception){
        exception.printStackTrace();
        return Result.failure("对不起,操作失败,请联系管理员");
    }
}
