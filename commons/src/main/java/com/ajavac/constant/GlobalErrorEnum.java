package com.ajavac.constant;

/**
 * 全局错误代号枚举类
 * Created by wyp0596 on 21/03/2017.
 */
public enum GlobalErrorEnum implements ErrorCode {
    SUCCESS(200, ""),//正确返回
    PARAM_ERROR(400, "param_error"),//参数错误
    UNKNOWN_ERROR(500, "unknown_error"),//未知错误
    NOT_FOUND(404, "not_found"),
    TIME_OUT(555, "超时"),
    ;

    private int code;
    private String msg;

    GlobalErrorEnum(int code, String msg) {
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
