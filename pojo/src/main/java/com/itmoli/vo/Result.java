package com.itmoli.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Result {
    private Integer code;

    private String msg;

    private Object data;


    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;

    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;

    }

    public static <T> Result success(T o){
        return new Result(1,"成功",o);
    }

    public static  Result err(){
        return new Result(0,"失败");
    }
    public static  Result success(){
        return new Result(1,"成功");
    }
    public static  Result err(String msg){

        return new Result(0,msg);
    }
}
