package com.example.domain.result;

public class Response {
    /** 返回数据*/
    private Object data;
    /** 返回信息码*/
    private String rspCode;
    /** 返回信息内容*/
    private String rspMsg;

    public Response(ExceptionMsg msg){
        this.rspCode=msg.getCode();
        this.rspMsg=msg.getMsg();
    }

    public Response(Object data, ExceptionMsg msg){
        this.data=data;
        this.rspCode=msg.getCode();
        this.rspMsg=msg.getMsg();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }
}
