package com.ajavac.config;

import com.ajavac.dto.JSONResponse;
import com.ajavac.enums.ErrorCodeEnum;
import com.ajavac.exception.MyRuntimeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制层异常处理器
 * Created by wyp0596 on 21/03/2017.
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public JSONResponse exceptionHandler(HttpServletRequest request,
                                         Exception exception) {
        exception.printStackTrace();
        if (exception instanceof MyRuntimeException) {
            MyRuntimeException e = (MyRuntimeException) exception;
            return new JSONResponse(e.getResponse());
        }
        return new JSONResponse(ErrorCodeEnum.UNKNOWN_ERROR);
    }
}
