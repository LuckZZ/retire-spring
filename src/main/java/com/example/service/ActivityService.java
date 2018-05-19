package com.example.service;

import com.example.domain.entity.Activity;
import org.springframework.data.domain.Page;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface ActivityService extends BaseCrudService<Activity,Integer>{

    Activity save(String activityName, String[] labels, String[] inputs);

    boolean existsByActivityName(String activityName);

    /*查询草稿活动*/
    Page<Activity> findAllDraft(Integer page);

    /*查询非草稿活动*/
    Page<Activity> findAllNotDraft(Integer page);

    /*根据组id，主要为组长，查询非草稿活动*/
    Page<Activity> findAllNotDraft(Integer page, Integer groupId);

    /*根据活动名称查询草稿活动*/
    Page<Activity> findAllDraftByActivityName(String activityName, Integer page);

    /*根据活动名称查询非草稿活动*/
    Page<Activity> findAllNotDraftByActivityName(String activityName, Integer page);

    int activityPublish(Integer activityId);

    boolean updateById(Integer activityId, String activityName, String[] labels, String[] inputs);

    boolean canJoin(Integer activityId);

    int notActivityStatus(Integer activityId);

}
