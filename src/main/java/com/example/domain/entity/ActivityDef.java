package com.example.domain.entity;

import com.example.utils.DataUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
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

//    把input分割，转化为inputs
    @Transient
    private List<String> inputs = new ArrayList<>();

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

    public List<String> getInputs() {
//        转化
        inputs = DataUtils.strToList(input);
        return inputs;
    }

    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
    }
}
