package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 职称
 * Duty实体类有属性:
 * dutyId、dutyName
 */
@Entity
@Table(name = "tb_duty")
public class Duty implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "duty_id")
    private Integer dutyId;     //主键、自增
    @Column(nullable = false)
    private String dutyName;    //非空

    public Duty() {
    }

    public Duty(String dutyName) {
        this.dutyName = dutyName;
    }

    //set、get方法

    public Integer getDutyId() {
        return dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }
}
