package com.example.domain.entity;

import com.example.domain.enums.Attend;

import javax.persistence.*;
import java.io.Serializable;

/**
 * JoinActivity实体类有属性:
 * join_activity_id、user_id、activity_id、place_name、expand
 */
@Entity
@Table(name = "tb_join")
public class Join implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "join_id")
    private  Integer joinActivityId;        //主键、自增

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;              //参加活动：用户 = 多对一

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;              //参加活动:活动 = 多对一

    //    自定义行
    @Column
    private String[] labelDefs;

//    与activity的inputDefs不同；如activity的inputDefs（地点1；地点2），此处为（地点1）或（地点2）
    @Column
    private String[] inputDefs;

//    是否参加活动字段
    @Column
    private Attend attend;

    //set、get方法

    public Integer getJoinActivityId() {
        return joinActivityId;
    }

    public void setJoinActivityId(Integer joinActivityId) {
        this.joinActivityId = joinActivityId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String[] getLabelDefs() {
        return labelDefs;
    }

    public void setLabelDefs(String[] labelDefs) {
        this.labelDefs = labelDefs;
    }

    public String[] getInputDefs() {
        return inputDefs;
    }

    public void setInputDefs(String[] inputDefs) {
        this.inputDefs = inputDefs;
    }

    public Attend getAttend() {
        return attend;
    }

    public void setAttend(Attend attend) {
        this.attend = attend;
    }
}
