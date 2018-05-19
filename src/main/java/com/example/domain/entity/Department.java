package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 部门
 * Department实体类有属性:
 * departmentId、departmentName
 *
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Entity
@Table(name = "tb_department")
public class Department implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    private Integer departmentId;
    @Column(nullable = false)
    private String departmentName;

    public Department() {
    }


    public Department(String departmentName) {
        this.departmentName = departmentName;
    }


    //set、get方法

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
