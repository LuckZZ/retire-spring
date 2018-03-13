package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用于民族
 * Nation实体类有属性：
 * nationId、nationName
 */
@Entity
@Table(name = "tb_nation")
public class Nation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nation_id")
    private Integer nationId;
    @Column(nullable = false)
    private String nationName;//非空

    //set、get方法

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }
}
