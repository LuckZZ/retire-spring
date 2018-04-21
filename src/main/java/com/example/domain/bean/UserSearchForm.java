package com.example.domain.bean;

/**
 * 用于用户高级搜索表单
 * Create by : Zhangxuemeng
 */
public class UserSearchForm {
//    组别
    private String group;
//    性别
    private String gender;
//      类型
    private String rank;
//    职务
    private String duty;
//     部门
    private String department;
//            离退类型
    private String exist;
//    政治面貌
    private String politics;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExist() {
        return exist;
    }

    public void setExist(String exist) {
        this.exist = exist;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    @Override
    public String toString() {
        return "UserSearchForm{" +
                "group='" + group + '\'' +
                ", gender='" + gender + '\'' +
                ", rank='" + rank + '\'' +
                ", duty='" + duty + '\'' +
                ", department='" + department + '\'' +
                ", exist='" + exist + '\'' +
                ", politics='" + politics + '\'' +
                '}';
    }
}
