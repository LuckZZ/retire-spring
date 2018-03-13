package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 性别
 * Gender实体类有属性：
 * genderId、genderName
 */
@Entity
@Table(name = "tb_gender")
public class Gender implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gender_id")
    private Integer genderId;
    @Column(nullable = false)
    private String genderName;//非空

    //set、get方法

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }
}
