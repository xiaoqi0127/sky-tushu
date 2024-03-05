package com.itmoli.exception;

import com.itmoli.constant.MsgConstant;
import com.itmoli.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){

        e.printStackTrace();
        return new Result(0, MsgConstant.UNKNOWN_ERR,null);

    }

    @ExceptionHandler(JwtException.class)
    public Result jwtException(JwtException e){

        e.printStackTrace();
        return new Result(0, MsgConstant.TOKEN_ERR,null);

    }
}
