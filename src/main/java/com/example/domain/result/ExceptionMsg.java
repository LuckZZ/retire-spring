package com.example.domain.result;

public enum ExceptionMsg {
    SUCCESS("000000", "操作成功"),
    FAILED("999999","操作失败"),
    ParamError("000001", "参数错误！"),

    LoginNameOrPassWordError("000100", "用户名或者密码错误！"),
    UserNameUsed("000101","工号已存在"),
    PassWordError("000102","密码输入错误");

    private String code;
    private String msg;

    /**
     *
     * @param code 信息码
     * @param msg 信息内容
     */
    private ExceptionMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
