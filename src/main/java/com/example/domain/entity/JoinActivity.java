package com.example.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * JoinActivity实体类有属性:
 * join_activity_id、user_id、activity_id、place_name、expand
 */
@Entity
@Table(name = "tb_join_activity")
public class JoinActivity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "join_activity_id")
    private  Integer joinActivityId;        //主键、自增

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;              //参加活动：用户 = 多对一

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;              //参加活动:活动 = 多对一

    @Column(nullable = false)
    private String placeName;            //报名地点非空
    @Column(nullable = false)
    private String expand;              //非空

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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }
}
