package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_demo")
public class Demo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//主键.
    private String name;//测试名称.
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
