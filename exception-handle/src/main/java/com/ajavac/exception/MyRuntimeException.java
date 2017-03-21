package com.ajavac.exception;

import com.ajavac.enums.ErrorCode;

/**
 * 自定义的运行时异常
 * Created by wyp0596 on 21/03/2017.
 */
public class MyRuntimeException extends RuntimeException {
    private ErrorCode response;

    public MyRuntimeException(ErrorCode response) {
        this.response = response;
    }

    public ErrorCode getResponse() {
        return response;
    }
}
