package com.example.serviceImpl;

import com.example.comm.Constant;
import com.example.dao.ActivityDao;
import com.example.dao.JoinDao;
import com.example.dao.UserDao;
import com.example.domain.entity.Activity;
import com.example.domain.enums.ActivityStatus;
import com.example.domain.enums.Exist;
import com.example.service.ActivityService;
import com.example.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ActivityServiceImpl extends BaseCrudServiceImpl<Activity,Integer,ActivityDao> implements ActivityService{
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JoinDao joinDao;

    @Override
    public Activity save(Activity activity) {
//        把活动保存到草稿箱
        activity.setActivityStatus(ActivityStatus.draft);
        return activityDao.save(activity);
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> activities = activityDao.findAll();
        for (Activity activity : activities) {
            activity = assignActivity(activity);
        }
        return activities;
    }

    @Override
    public boolean existsByActivityName(String activityName) {
        return activityDao.existsByActivityName(activityName);
    }

    @Override
    public Page<Activity> findAllByActivityStatus(ActivityStatus activityStatus, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<Activity> activities = activityDao.findAllByActivityStatus(activityStatus, pageable);
        for (Activity activity : activities) {
            activity = assignActivity(activity);
        }
        return activities;
    }

    @Override
    public Page<Activity> findAllByActivityStatusNot(ActivityStatus activityStatus, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<Activity> activities = activityDao.findAllByActivityStatusNot(activityStatus, pageable);
        for (Activity activity : activities) {
            activity = assignActivity(activity);
        }
        return activities;
    }

    @Transactional
    @Override
    public int activityPublish(Integer activityId) {
        return activityDao.updateActivityStatus(ActivityStatus.close,activityId);
    }

    @Transactional
    @Override
    public boolean updateExceptId(Activity activity) {
        if(activity.getActivityId() == null){
            return false;
        }
//        保存到草稿箱
        activity.setActivityStatus(ActivityStatus.draft);
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
        if (activityId == null){
            return null;
        }
        Activity activity = activityDao.findOne(activityId);
        activity = assignActivity(activity);
        return activity;
    }

    private Activity assignActivity(Activity activity){
        String[] strings = activity.getInputDefs();
        String[][] strings1 = DataUtils.oneStrToTwoStr(strings);
        long joinOkSize = joinDao.countByActivity_ActivityId(activity.getActivityId());
        long userCount = userDao.countByExist(Exist.yes);

        activity.setInputDefss(strings1);
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
