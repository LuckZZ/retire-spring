package com.example.domain.entity;

import javax.persistence.*;

/**
 * 组长表
 */
@Entity
@Table(name = "tb_grouper")
public class Grouper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private Integer grouperId;      //id
    @Column(nullable = false)
    private String password;    //密码不为空
    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    private User user;          //用户

    @Column(nullable = false)
    private String lastTime;        //最后登录日期

    public Grouper() {
    }
    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
    public Integer getGrouperId() {
        return grouperId;
    }

    public void setGrouperId(Integer grouperId) {
        this.grouperId = grouperId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
