package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Group实体类有属性：
 * group_id、group_name
 */
@Entity
@Table(name = "tb_group")
public class Group extends BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private Integer groupId;    //组Id为主键、自增

    @Column(nullable = false,unique = true)
    private String groupName;   //组名称非空、唯一

    @Transient
    private List<Grouper> groupers = new ArrayList<>();

//    组长们姓名
    @Transient
    private String groupersName;

    @Transient
    private long count;

    public Group() {

    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    //set、get方法

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Grouper> getGroupers() {
        return groupers;
    }

    public void setGroupers(List<Grouper> groupers) {
        this.groupers = groupers;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getGroupersName() {
        return groupersName;
    }

    public void setGroupersName(String groupersName) {
        this.groupersName = groupersName;
    }
}
