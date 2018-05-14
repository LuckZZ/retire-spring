package com.example.domain.entity;

import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Verify;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 组长表
 */
@Entity
@Table(name = "tb_grouper")
public class Grouper extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "grouper_id")
    private Integer grouperId;      //id

    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    private User user;          //用户

    @Column(nullable = false)
    private String password;    //密码不为空

    @Column(nullable = true)
    private String lastTime;        //最近登录时间可以为空

    @Column(nullable = false)
    private CanLogin canLogin;      //是否允许登陆

    @Column
    private String email;

    @Column
    private Verify verify;

    @Column
    private String verifyCode;

    @Column
    private String codeTime;

    public Grouper() {
    }

    public Grouper(User user, String password, CanLogin canLogin) {
        this.user = user;
        this.password = password;
        this.canLogin = canLogin;
    }

    public Integer getGrouperId() {
        return grouperId;
    }

    public void setGrouperId(Integer grouperId) {
        this.grouperId = grouperId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getCodeTime() {
        return codeTime;
    }

    public void setCodeTime(String codeTime) {
        this.codeTime = codeTime;
    }
}
