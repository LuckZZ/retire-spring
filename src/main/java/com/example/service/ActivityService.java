package com.example.service;

import com.example.domain.entity.Activity;
import com.example.domain.enums.ActivityStatus;

import java.util.List;

public interface ActivityService extends BaseCrudService<Activity,Integer>{

    List<Activity> findAllByActivityStatus(ActivityStatus activityStatus);

    List<Activity> findAllByActivityStatusNot(ActivityStatus activityStatus);

}
