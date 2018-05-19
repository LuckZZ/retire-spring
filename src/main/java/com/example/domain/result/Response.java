package com.example.domain.result;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public class Response {
    /** 返回数据*/
    private Object data;
    /** 返回信息码*/
    private Boolean codeBool;
    /** 返回信息内容*/
    private String message;

    public Response(ExceptionMsg msg){
        this.codeBool=msg.isCodeBool();
        this.message=msg.getMessage();
    }

    public Response(Object data, ExceptionMsg msg){
        this.data=data;
        this.codeBool=msg.isCodeBool();
        this.message=msg.getMessage();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getCodeBool() {
        return codeBool;
    }

    public void setCodeBool(Boolean codeBool) {
        this.codeBool = codeBool;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
