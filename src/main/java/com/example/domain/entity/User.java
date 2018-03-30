package com.example.domain.entity;

import com.example.domain.enums.Exist;
import com.example.domain.enums.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * User实体类有属性:
 * user_id、group_id、job_num、name、
 * gender、tel、
 * mate、address、department、duties、work_time、
 * retire_time、birth、nation、exist、photo、
 * pass_time、politics、createTinme
 */
@Entity
@Table(name="tb_user")
public class User extends BaseEntity implements Serializable,Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Integer userId;         //用户Id为主键、自增

    @Column(nullable = false)
    private String jobNum;      //工号非空

    @Column(nullable = false)
    private String name;        //姓名非空

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;        //多对一,多的一方数据表生成group_id列

    @Column
    private Gender gender;

    @Column
    private String tel1;

    @Column
    private String tel2;

    @Column
    private String tel3;

    @Column
    private String mate;

    @Column
    private String address;

    @ManyToOne
    @JoinColumn(name = "politics_id")
    private Politics politics;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "duty_id")
    private Duty duty;

    @ManyToOne
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @Column(nullable = false)
    private Exist exist;          //是否存在非空

    @Column
    private String imgUrl;             //图片地址

    @Column
    private String passTime;

    @Column
    private String birth;

    @Column
    private String workTime;

    @Column
    private String retireTime;

    @Column
    private String other;
    //set、get方法


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getMate() {
        return mate;
    }

    public void setMate(String mate) {
        this.mate = mate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Politics getPolitics() {
        return politics;
    }

    public void setPolitics(Politics politics) {
        this.politics = politics;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public Exist getExist() {
        return exist;
    }

    public void setExist(Exist exist) {
        this.exist = exist;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getRetireTime() {
        return retireTime;
    }

    public void setRetireTime(String retireTime) {
        this.retireTime = retireTime;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    /**
     * 克隆对象方法
     * @return
     */
    @Override
    public Object clone(){
        User user = null;
        try{
            user = (User)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return user;
    }

}
