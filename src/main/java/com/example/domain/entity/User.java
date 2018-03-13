package com.example.domain.entity;

import com.example.domain.enums.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * User实体类有属性:
 * user_id、group_id、job_num、name、
 * gender、tel、cel1、cel2、
 * mate、address、department、duties、work_time、
 * retire_time、birth、nation、exist、photo、
 * pass_time、politics、createTinme
 */
@Entity
@Table(name="tb_user")
public class User implements Serializable,Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Integer userId;         //用户Id为主键、自增

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;        //多对一,多的一方数据表生成group_id列

    @Column(nullable = false)
    private String jobNum;      //工号非空

    @Column(nullable = false)
    private String name;        //姓名非空

    @Column
    private Gender gender;

    private String tel;
    private String cel1;
    private String cel2;
    private String mate;
    private String address;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "duty_id")
    private Duty duty;

    private Date workTime;
    private Date retireTime;
    private Date birth;

    @ManyToOne
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @Column(nullable = false)
    private Integer exist;          //是否存在非空
    private String photo;
    private Integer passTime;

    @ManyToOne
    @JoinColumn(name = "politics_id")
    private Politics politics;

    @Column
    private String createTime;  //用户创建时间
    //set、get方法

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCel1() {
        return cel1;
    }

    public void setCel1(String cel1) {
        this.cel1 = cel1;
    }

    public String getCel2() {
        return cel2;
    }

    public void setCel2(String cel2) {
        this.cel2 = cel2;
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

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public Date getRetireTime() {
        return retireTime;
    }

    public void setRetireTime(Date retireTime) {
        this.retireTime = retireTime;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public Integer getExist() {
        return exist;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getPassTime() {
        return passTime;
    }

    public void setPassTime(Integer passTime) {
        this.passTime = passTime;
    }

    public Politics getPolitics() {
        return politics;
    }

    public void setPolitics(Politics politics) {
        this.politics = politics;
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
