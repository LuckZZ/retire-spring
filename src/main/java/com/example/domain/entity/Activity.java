package com.example.domain.entity;

import com.example.domain.enums.ActivityStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Activtiy实体类有属性：
 * activity_id、activityName、labelDefs、inputDefs、activityStatus
 */
@Entity
@Table(name = "tb_activity")
public class Activity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private Integer activityId;         //活动id为主键且自增

    @Column(nullable = false)
    private String activityName;    //活动名称非空

//    自定义行
    @Column
    private String[] labelDefs;

    @Column
    private String[] inputDefs;

    //    活动状态
    @Column
    private ActivityStatus activityStatus;

//    不生成列
    @Transient
    private String[][] inputDefss;

    //    不生成列
    @Transient
    private long joinOkCount;
    //    不生成列
    @Transient
    private long userCount;

    public Activity() {
    }

    public Activity(String activityName, String[] labelDefs, String[] inputDefs) {
        this.activityName = activityName;
        this.labelDefs = labelDefs;
        this.inputDefs = inputDefs;
    }

    //set、get方法

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }


    public String[] getLabelDefs() {
        return labelDefs;
    }

    public void setLabelDefs(String[] labelDefs) {
        this.labelDefs = labelDefs;
    }

    public String[] getInputDefs() {
        return inputDefs;
    }

    public void setInputDefs(String[] inputDefs) {
        this.inputDefs = inputDefs;
    }

    public String[][] getInputDefss() {
        return inputDefss;
    }

    public void setInputDefss(String[][] inputDefss) {
        this.inputDefss = inputDefss;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }

    public long getJoinOkCount() {
        return joinOkCount;
    }

    public void setJoinOkCount(long joinOkCount) {
        this.joinOkCount = joinOkCount;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }
}
