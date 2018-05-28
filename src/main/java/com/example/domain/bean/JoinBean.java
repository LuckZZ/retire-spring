package com.example.domain.bean;

/**
 * Create by : Zhangxuemeng on 2018/5/27
 * Csdn blog：https://blog.csdn.net/Luck_ZZ
 */
public class JoinBean {
    private Integer userId;
    private Integer activityId;
    private String[] inputDefs;
    private String attend;
    private String other;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String[] getInputDefs() {
        return inputDefs;
    }

    public void setInputDefs(String[] inputDefs) {
        this.inputDefs = inputDefs;
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
