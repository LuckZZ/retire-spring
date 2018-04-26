package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Create by : Zhangxuemeng
 */
@Entity
@Table(name = "tb_activity_def")
public class ActivityDef extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private  Integer id;        //主键、自增

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String input;

    public ActivityDef() {
    }

    public ActivityDef(String label, String input) {
        this.label = label;
        this.input = input;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

}
