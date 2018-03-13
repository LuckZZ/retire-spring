package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 部门
 * Department实体类有属性:
 * departmentId、departmentName
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
