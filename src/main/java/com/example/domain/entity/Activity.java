package com.example.domain.entity;

import com.example.domain.enums.ActivityStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
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

    //    活动状态
    @Column
    private ActivityStatus activityStatus;

    //    不生成列
    @Transient
    private long joinOkCount;
    //    不生成列
    @Transient
    private long userCount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_id")
    private List<ActivityDef> activityDefs = new ArrayList<>();

    public Activity() {
    }

    public Activity(String activityName, List<ActivityDef> activityDefs, ActivityStatus activityStatus) {
        this.activityName = activityName;
        this.activityStatus = activityStatus;
        this.activityDefs = activityDefs;
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

    public List<ActivityDef> getActivityDefs() {
        return activityDefs;
    }

    public void setActivityDefs(List<ActivityDef> activityDefs) {
        this.activityDefs = activityDefs;
    }
}
