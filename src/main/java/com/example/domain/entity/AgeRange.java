package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用于年龄统计
 * AgeRange实体类有属性:
 * ageRangeId、ageRangeName
 */
@Entity
@Table(name = "tb_age_range")
public class AgeRange implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "age_range_id")
    private Integer ageRangeId;   //主键、自增

    @Column(nullable = false)
    private String ageRangeName;  //非空

    //set、get方法

    public Integer getAgeRangeId() {
        return ageRangeId;
    }

    public void setAgeRangeId(Integer ageRangeId) {
        this.ageRangeId = ageRangeId;
    }

    public String getAgeRangeName() {
        return ageRangeName;
    }

    public void setAgeRangeName(String ageRangeName) {
        this.ageRangeName = ageRangeName;
    }
}
