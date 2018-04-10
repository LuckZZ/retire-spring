package com.example.domain.bean;

import com.example.domain.entity.Activity;
import com.example.domain.entity.User;

import java.util.List;

/**
 * 用于报名
 */
public class JoinsDisplay {
    private List<User> users;
    private Activity activity;

    public JoinsDisplay() {
    }

    public JoinsDisplay(List<User> users, Activity activity) {
        this.users = users;
        this.activity = activity;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
