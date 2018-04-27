package com.example.service;

import com.example.RetireSpringApplication;
import com.example.dao.ActivityDao;
import com.example.domain.entity.Activity;
import com.example.domain.entity.ActivityDef;
import com.example.domain.entity.Group;
import com.example.domain.enums.ActivityStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ActivityServiceTest {
    @Autowired
    private ActivityDao activityDao;

    @Test
    public void save(){
        Set<ActivityDef> activityDefs = new LinkedHashSet<>();
        activityDefs.add(new ActivityDef("a","b"));
        activityDefs.add(new ActivityDef("c","d"));
        activityDefs.add(new ActivityDef("e","f"));
        Activity activity = new Activity("活动测试",activityDefs,ActivityStatus.draft);
        activityDao.save(activity);

    }

    @Test
    public void findAll(){
        Activity activity = activityDao.findOne(6);
        Set<ActivityDef> activityDefs = activity.getActivityDefs();
        for (ActivityDef activityDef : activityDefs){
            System.out.println("lab:"+activityDef.getLabel()+" input:"+activityDef.getInput());
        }
    }

}
