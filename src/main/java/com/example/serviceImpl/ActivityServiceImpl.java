package com.example.serviceImpl;

import com.example.dao.ActivityDao;
import com.example.domain.entity.Activity;
import com.example.domain.enums.ActivityStatus;
import com.example.service.ActivityService;
import com.example.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ActivityServiceImpl extends BaseCrudServiceImpl<Activity,Integer,ActivityDao> implements ActivityService{
    @Autowired
    private ActivityDao activityDao;

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
            String[] strings = activity.getInputDefs();
            String[][] strings1 = DataUtils.oneStrToTwoStr(strings);
            activity.setInputDefss(strings1);
        }
        return activities;
    }

    @Override
    public boolean existsByActivityName(String activityName) {
        return activityDao.existsByActivityName(activityName);
    }

    /**
     * 草稿活动列表
     * @param activityStatus
     * @return
     */
    @Override
    public List<Activity> findAllByActivityStatus(ActivityStatus activityStatus) {
        List<Activity> activities = activityDao.findAllByActivityStatus(activityStatus);
        for (Activity activity : activities) {
            String[] strings = activity.getInputDefs();
            String[][] strings1 = DataUtils.oneStrToTwoStr(strings);
            activity.setInputDefss(strings1);
        }
        return activities;
    }

    /**
     * 非草稿活动列表
     * @param activityStatus
     * @return
     */
    @Override
    public List<Activity> findAllByActivityStatusNot(ActivityStatus activityStatus) {
        List<Activity> activities = activityDao.findAllByActivityStatusNot(activityStatus);
        for (Activity activity : activities) {
            String[] strings = activity.getInputDefs();
            String[][] strings1 = DataUtils.oneStrToTwoStr(strings);
            activity.setInputDefss(strings1);
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
     * 活动开启中，不能删除
     * @param activityId
     * @return
     */
    @Override
    public boolean canDelete(Integer activityId) {
        Activity activity = activityDao.findOne(activityId);
        if (activity.getActivityStatus() == ActivityStatus.open){
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
        String[] strings = activity.getInputDefs();
        String[][] strings1 = DataUtils.oneStrToTwoStr(strings);
        activity.setInputDefss(strings1);
        return activity;
    }
}
