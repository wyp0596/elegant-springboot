package com.ajavac.dto;

import com.ajavac.enums.ErrorCode;
import com.ajavac.enums.ErrorCodeEnum;

/**
 * JSON返回对象
 * Created by wyp0596 on 21/03/2017.
 */
public class JSONResponse<T> {

    private int code;

    private String msg;

    private T data;

    private JSONResponse(ErrorCode response, T data) {
        this.code = response.getCode();
        this.msg = response.getMsg();
        this.data = data;
    }

    public JSONResponse(T data) {
        this(ErrorCodeEnum.SUCCESS, data);
    }

    public JSONResponse(ErrorCode response) {
        this(response, null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
