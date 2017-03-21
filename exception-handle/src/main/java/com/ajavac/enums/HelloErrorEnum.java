package com.ajavac.enums;

/**
 * 业务错误代号枚举类
 * Created by wyp0596 on 21/03/2017.
 */
public enum HelloErrorEnum implements ErrorCode {
    NAME_EXIST(1001, "名字已存在"),
    ;

    private int code;
    private String msg;

    HelloErrorEnum(int code, String msg) {
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
