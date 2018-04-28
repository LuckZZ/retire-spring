package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Create by : Zhangxuemeng
 */
@Entity
@Table(name = "tb_join_def")
public class JoinDef extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private  Integer id;        //主键、自增

    @OneToOne
    @JoinColumn(name = "activity_def_id")
    private ActivityDef activityDef;

    @Column
    private String input;

    public JoinDef() {
    }

    public JoinDef(ActivityDef activityDef, String input) {
        this.activityDef = activityDef;
        this.input = input;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ActivityDef getActivityDef() {
        return activityDef;
    }

    public void setActivityDef(ActivityDef activityDef) {
        this.activityDef = activityDef;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

}
