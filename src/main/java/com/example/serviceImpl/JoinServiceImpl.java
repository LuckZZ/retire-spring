package com.example.serviceImpl;

import com.example.dao.ActivityDao;
import com.example.dao.JoinDao;
import com.example.dao.UserDao;
import com.example.domain.bean.JoinsDisplay;
import com.example.domain.entity.Activity;
import com.example.domain.entity.Join;
import com.example.domain.entity.User;
import com.example.service.ActivityService;
import com.example.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JoinServiceImpl extends BaseCrudServiceImpl<Join, Integer, JoinDao> implements JoinService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private JoinDao joinDao;

    @Autowired
    private ActivityService activityService;

    @Override
    public JoinsDisplay joinNo(Integer activityId) {

        //      活动
        Activity activity = activityService.findOne(activityId);

        List<Join> joins = joinDao.findAllByActivity(activity);

//        参加的用户id
        List<Integer> userIds = new ArrayList<>();

//        报名id
        for (Join join : joins){
            userIds.add(join.getUser().getUserId());
        }

//        未报名的用户
        List<User> users;

        if (userIds.size() == 0){
            users = userDao.findAll();
        }else {
            users = userDao.findAllByUserIdNotIn(userIds);
        }

        JoinsDisplay joinDisplays = new JoinsDisplay(users, activity);

        return joinDisplays;
    }
}
