package com.example.serviceImpl;

import com.example.comm.Constant;
import com.example.dao.ActivityDao;
import com.example.dao.ActivityDefDao;
import com.example.dao.JoinDao;
import com.example.dao.UserDao;
import com.example.domain.entity.Activity;
import com.example.domain.entity.ActivityDef;
import com.example.domain.enums.ActivityStatus;
import com.example.domain.enums.Exist;
import com.example.domain.enums.JoinStatus;
import com.example.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Service
public class ActivityServiceImpl extends BaseCrudServiceImpl<Activity,Integer,ActivityDao> implements ActivityService{
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityDefDao activityDefDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JoinDao joinDao;

    @Override
    public Activity save(String activityName, String[] labels, String[] inputs) {
        List<ActivityDef> activityDefs = new ArrayList<>();
        if (labels != null && inputs != null){
            for (int i = 0; i < labels.length; i ++){
                activityDefs.add(new ActivityDef(labels[i], inputs[i]));
            }
        }
        Activity activity = new Activity(activityName, activityDefs, ActivityStatus.draft);
        return activityDao.save(activity);
    }

    @Override
    public boolean existsByActivityName(String activityName) {
        return activityDao.existsByActivityName(activityName);
    }

    @Override
    public Page<Activity> findAllDraft(Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<Activity> activities = activityDao.findAllByActivityStatus(ActivityStatus.draft, pageable);
        for (Activity activity : activities) {
            activity = assignActivity(activity);
        }
        return activities;
    }

    @Override
    public Page<Activity> findAllNotDraft(Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<Activity> activities = activityDao.findAllByActivityStatusNot(ActivityStatus.draft, pageable);
        activities.forEach(activity -> activity = assignActivity(activity));
        return activities;
    }

    @Override
    public Page<Activity> findAllNotDraft(Integer page, Integer groupId) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<Activity> activities = activityDao.findAllByActivityStatusNot(ActivityStatus.draft, pageable);
        activities.forEach(activity -> activity = assignActivityWithGroupId(groupId, activity));
        return activities;
    }

    @Override
    public Page<Activity> findAllDraftByActivityNameContaining(String activityName, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<Activity> activities = activityDao.findAllByActivityStatusAndActivityNameContaining(ActivityStatus.draft, activityName, pageable);
        activities.forEach(activity -> activity = assignActivity(activity));
        return activities;
    }

    @Override
    public Page<Activity> findAllNotDraftByActivityNameContaining(String activityName, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<Activity> activities = activityDao.findAllByActivityStatusNotAndActivityNameContaining(ActivityStatus.draft, activityName, pageable);
        activities.forEach(activity -> activity = assignActivity(activity));
        return activities;
    }

    @Transactional
    @Override
    public int activityPublish(Integer activityId) {
        return activityDao.updateActivityStatus(ActivityStatus.close,activityId);
    }

    @Transactional
    @Override
    public boolean updateById(Integer activityId, String activityName, String[] labels, String[] inputs) {
//        删除activityDef表数据
        activityDefDao.deleteAllByActivityId(activityId);
        List<ActivityDef> activityDefs = new ArrayList<>();
        for (int i = 0; i < labels.length; i ++){
            activityDefs.add(new ActivityDef(labels[i], inputs[i]));
        }
        Activity activity = activityDao.getOne(activityId);
        activity.setActivityName(activityName);
        activity.setActivityDefs(activityDefs);
        activityDao.save(activity);
        return true;
    }

    /**
     * 活动关闭中，不能报名和删除报名
     * @param activityId
     * @return
     */
    @Override
    public boolean canJoin(Integer activityId) {
        Activity activity = activityDao.findOne(activityId);
        if (activity.getActivityStatus() == ActivityStatus.close){
            return false;
        }
        return true;
    }

    /**
     * 转换活动状态
     * @param activityId
     * @return
     */
    @Transactional
    @Override
    public int notActivityStatus(Integer activityId) {

        Activity activity = activityDao.findOne(activityId);

        if (activity.getActivityStatus() == ActivityStatus.open){
            return activityDao.updateActivityStatus(ActivityStatus.close,activityId);
        }else if (activity.getActivityStatus() == ActivityStatus.close){
            return activityDao.updateActivityStatus(ActivityStatus.open,activityId);
        }

        return 0;
    }

    /**
     * 根据id查找一条数据
     * @param activityId
     * @return
     */
    @Override
    public Activity findOne(Integer activityId) {
        Activity activity = Optional.ofNullable(activityId).map(id -> activityDao.findOne(activityId)).orElse(null);
        if (activity != null){
            activity = assignActivity(activity);
        }
        return activity;
    }

    private Activity assignActivity(Activity activity){
        long joinOkSize = joinDao.countByActivity_ActivityIdAndUser_ExistAndJoinStatus(activity.getActivityId(), Exist.yes, JoinStatus.ultima);
        long userCount = userDao.countByExist(Exist.yes);
        activity.setJoinOkCount(joinOkSize);
        activity.setUserCount(userCount);
        return activity;
    }

    private Activity assignActivityWithGroupId(Integer groupId, Activity activity){
        long joinOkSize = joinDao.countByUser_Group_GroupIdAndActivity_ActivityIdAndUser_ExistAndJoinStatus(groupId, activity.getActivityId(), Exist.yes, JoinStatus.ultima);
        long userCount = userDao.countByGroup_GroupIdAndExist(groupId, Exist.yes);
        activity.setJoinOkCount(joinOkSize);
        activity.setUserCount(userCount);
        return activity;
    }

    /**
     * 先删除join表，再删除activity
     * @param activityId
     */
    @Transactional
    @Override
    public void delete(Integer activityId) {
       joinDao.deleteAllByActivity_ActivityId(activityId);
       activityDao.delete(activityId);
    }
}
