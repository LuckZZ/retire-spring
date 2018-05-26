package com.example.domain.entity;

import com.example.domain.enums.Attend;
import com.example.domain.enums.JoinStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Entity
@Table(name = "tb_join")
public class Join extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "join_id")
    private  Integer joinId;        //主键、自增

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;              //参加活动：用户 = 多对一

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;              //参加活动:活动 = 多对一

    //    是否参加活动字段
    @Column
    private Attend attend;

    // 备注
    @Column
    private String other;

    // 报名状态
    @Column
    private JoinStatus joinStatus;

    @OneToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    @JoinColumn(name = "join_id")
    private List<JoinDef> joinDefs = new ArrayList<>();

    public Join() {
    }

    public Join(User user, Activity activity, List<JoinDef> joinDefs, Attend attend, String other, JoinStatus joinStatus) {
        this.user = user;
        this.activity = activity;
        this.attend = attend;
        this.joinDefs = joinDefs;
        this.other = other;
        this.joinStatus = joinStatus;
    }

    //set、get方法

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
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

    public Attend getAttend() {
        return attend;
    }

    public void setAttend(Attend attend) {
        this.attend = attend;
    }

    public List<JoinDef> getJoinDefs() {
        return joinDefs;
    }

    public void setJoinDefs(List<JoinDef> joinDefs) {
        this.joinDefs = joinDefs;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public JoinStatus getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(JoinStatus joinStatus) {
        this.joinStatus = joinStatus;
    }
}