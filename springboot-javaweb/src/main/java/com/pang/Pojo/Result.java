package com.pang.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.util.RecordUtil;


@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    private Result(Integer code,String msg,Object object){
        this.code = code;
        this.msg = msg;
        this.data = object;
    }
    public static Result success(){
        return new Result(1,"success",null);
    }

    public static Result success(Object object){
        return new Result(1,"success",object);
    }

    public static Result failure(String msg){
        return new Result(0,msg,null);
    }
}
