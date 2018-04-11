package com.example.service;

import com.example.domain.entity.Activity;
import com.example.domain.enums.ActivityStatus;

import java.util.List;

public interface ActivityService extends BaseCrudService<Activity,Integer>{

    boolean existsByActivityName(String activityName);

    List<Activity> findAllByActivityStatus(ActivityStatus activityStatus);

    List<Activity> findAllByActivityStatusNot(ActivityStatus activityStatus);

    int activityPublish(Integer activityId);

    boolean updateExceptId(Activity activity);

    boolean canDelete(Integer activityId);

    int notActivityStatus(Integer activityId);

}
