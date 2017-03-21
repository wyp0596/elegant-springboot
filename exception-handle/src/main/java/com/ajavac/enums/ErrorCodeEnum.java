package com.ajavac.enums;

/**
 * 错误代号枚举类
 * Created by wyp0596 on 21/03/2017.
 */
public enum ErrorCodeEnum implements ErrorCode {
    SUCCESS(200, ""),//正确返回
    UNKNOWN_ERROR(500, "unknown_error"),//未知错误
    NOT_FOUND(404, "not_found"),
    ;

    private int code;
    private String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
