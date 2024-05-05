package com.example.useradmin.exception;


import com.example.useradmin.common.BaseResponse;
import com.example.useradmin.common.ErrorCode;
import com.example.useradmin.common.ResutUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice//spring AOP切面
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)//捕获业务异常,只捕获BusinessException相关
    public BaseResponse businessExceptionHandler(BusinessException e)
    {
        log.error("BusinessException: "+e.getMessage(),e);
        return ResutUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e)
        {
            log.error("RuntimeException",e);
            return ResutUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
        }


}
