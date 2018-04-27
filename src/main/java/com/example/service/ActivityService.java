package com.example.service;

import com.example.domain.entity.Activity;
import com.example.domain.enums.ActivityStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActivityService extends BaseCrudService<Activity,Integer>{

    Activity save(String activityName, String[] labels, String[] inputs);

    boolean existsByActivityName(String activityName);

    Page<Activity> findAllByActivityStatus(ActivityStatus activityStatus, Integer page);

    Page<Activity> findAllByActivityStatusNot(ActivityStatus activityStatus, Integer page);

    Page<Activity> findAllByActivityStatusAndActivityName(ActivityStatus activityStatus, String activityName, Integer page);

    Page<Activity> findAllByActivityStatusNotAndActivityName(ActivityStatus activityStatus, String activityName, Integer page);

    int activityPublish(Integer activityId);

    boolean updateById(Integer activityId, String activityName, String[] labels, String[] inputs);

    boolean canJoin(Integer activityId);

    int notActivityStatus(Integer activityId);

}
