package com.example.domain.entity;

import com.example.domain.enums.CanLogin;

import javax.persistence.*;

/**
 * 管理员表
 */
@Entity
@Table(name="tb_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="admin_id")
    private Integer adminId;         //用户Id为主键、自增

    @Column(nullable = false)
    private String jobNum;      //工号非空

    @Column(nullable = false)
    private String name;        //姓名

    @Column(nullable = false)
    private String password;        //密码非空

    @Column(nullable = false)
    private String createTime;      //创建时间

    @Column(nullable = true)
    private String lastTime;        //最后登录日可以为空

    @Column(nullable = false)
    private CanLogin canLogin;      //是否允许登陆

    public Admin() {
    }

    public Admin(String jobNum, String name, String password, String createTime, CanLogin canLogin) {
        this.jobNum = jobNum;
        this.name = name;
        this.password = password;
        this.createTime = createTime;
        this.canLogin = canLogin;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public CanLogin getCanLogin() {
        return canLogin;
    }

    public void setCanLogin(CanLogin canLogin) {
        this.canLogin = canLogin;
    }
}
