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

    @Column
    private String label;

    @Column
    private String input;

    @ManyToOne
    @JoinColumn(name="join_id")
    private Join join;

    public JoinDef() {
    }

    public JoinDef(String label, String input) {
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

    public Join getJoin() {
        return join;
    }

    public void setJoin(Join join) {
        this.join = join;
    }
}
