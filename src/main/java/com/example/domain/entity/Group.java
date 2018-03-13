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
public class Group implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private Integer groupId;    //组Id为主键、自增

    @Column(nullable = false,unique = true)
    private String groupName;   //组名称非空、唯一

    @OneToMany(mappedBy="group")
    private List<User> user = new ArrayList<>();    //一对多

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
}
