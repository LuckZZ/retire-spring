package com.example.domain.entity;

import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Verify;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 管理员表
 */
@Entity
@Table(name="tb_admin")
public class Admin extends BaseEntity implements Serializable{
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

    @Column(nullable = true)
    private String lastTime;        //最近登录时间可以为空

    @Column(nullable = false)
    private CanLogin canLogin;      //是否允许登陆

    @Column
    private String imgUrl;             //图片地址

    @Column
    private String email;

    @Column
    private Verify verify;

    public Admin() {
    }

    public Admin(String jobNum, String name, String password, CanLogin canLogin) {
        this.jobNum = jobNum;
        this.name = name;
        this.password = password;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Verify getVerify() {
        return verify;
    }

    public void setVerify(Verify verify) {
        this.verify = verify;
    }
}
