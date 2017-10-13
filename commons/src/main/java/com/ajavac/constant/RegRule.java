package com.ajavac.constant;

/**
 * 正则规则
 * Created by wyp0596 on 31/03/2017.
 */
public final class RegRule {

    private RegRule(){}

    //名称类:1-40个字符(不允许空格)
    public static final String NAME = "^[^\\s\\u3000]{1,40}$";
    //代号类(包括账号):1-40个英文或数字(半角,不允许空格)
    public static final String CODE = "^[a-zA-Z0-9]{1,40}$";
    //密码类:6-20个英文、字符和数字(半角,不允许空格)
    public static final String PASSWORD = "^[\\u0021-\\u007e]{6,20}$";
    //描述类:0-255个字符
    public static final String DESCRIPTION = "^.{0,255}$";
    //版本号号类(包括账号):1-40个英文或数字(半角,不允许空格,允许小数点)
    public static final String VERSION = "^[a-zA-Z0-9\\.]{1,40}$";

}
