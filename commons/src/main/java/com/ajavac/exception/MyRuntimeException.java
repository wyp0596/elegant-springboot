package com.ajavac.exception;


import com.ajavac.constant.ErrorCode;
import com.ajavac.constant.GlobalErrorEnum;

/**
 * 自定义的运行时异常
 * Created by wyp0596 on 21/03/2017.
 */
public class MyRuntimeException extends RuntimeException {
    private ErrorCode response;

    public MyRuntimeException(ErrorCode response) {
        super(response.getMsg());
        this.response = response;
    }

    public MyRuntimeException(String message) {
        super(message);
        this.response = GlobalErrorEnum.UNKNOWN_ERROR;
    }

    public MyRuntimeException(Throwable cause) {
        super(cause);
        this.response = GlobalErrorEnum.UNKNOWN_ERROR;
    }

    public MyRuntimeException(ErrorCode response, Throwable cause) {
        super(response.getMsg(), cause);
        this.response = response;
    }

    public MyRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.response = response;
    }

    public ErrorCode getResponse() {
        return response;
    }
}
