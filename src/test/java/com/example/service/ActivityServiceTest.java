package com.example.service;

import com.example.RetireSpringApplication;
import com.example.dao.ActivityDao;
import com.example.domain.entity.Activity;
import com.example.domain.entity.Group;
import com.example.domain.enums.ActivityStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ActivityServiceTest {
    @Autowired
    private ActivityDao activityDao;

    @Test
    public void save(){
        String[] labelDefs = new String[]{"Labe1One","LabelTwo"};
        String[][] inputDefss = new String[][]{{"One111","One222"},{"Two111","Two222"}};
        Activity activity = new Activity();
        activity.setActivityName("活动一");
        activity.setLabelDefs(labelDefs);
        activity.setInputDefss(inputDefss);
        activity.setActivityStatus(ActivityStatus.draft);
        activityDao.save(activity);
    }

}
