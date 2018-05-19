package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 政治面貌
 * Politics实体类有属性：
 * politicsId、politicsName
 *
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Entity
@Table(name = "tb_politics")
public class Politics implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "politics_id")
    private Integer politicsId;
    @Column(nullable = false)
    private String politicsName;

    public Politics() {
    }

    public Politics(String politicsName) {
        this.politicsName = politicsName;
    }

    //set、get方法

    public Integer getPoliticsId() {
        return politicsId;
    }

    public void setPoliticsId(Integer politicsId) {
        this.politicsId = politicsId;
    }

    public String getPoliticsName() {
        return politicsName;
    }

    public void setPoliticsName(String politicsName) {
        this.politicsName = politicsName;
    }
}
