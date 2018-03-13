package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Activtiy实体类有属性：
 * activity_id、activityName、placeName、expand、exist、
 * year
 */
@Entity
@Table(name = "tb_activity")
public class Activity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private Integer activityId;         //活动id为主键且自增

    @Column(nullable = false)
    private String activityName;    //活动名称非空
    private String placeName;
    private String expand;

    @Column(nullable = false)
    private Integer exist;          //活动存在状态非空

    @Column(nullable = true)
    private Integer year;   //活动年份可以为空

    @OneToMany(mappedBy = "activity")
    private List<JoinActivity> joinActivities = new ArrayList<>();  //一对多
    //set、get方法

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public int getExist() {
        return exist;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<JoinActivity> getJoinActivities() {
        return joinActivities;
    }

    public void setJoinActivities(List<JoinActivity> joinActivities) {
        this.joinActivities = joinActivities;
    }
}
