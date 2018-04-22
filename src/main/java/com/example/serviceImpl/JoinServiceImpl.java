package com.example.serviceImpl;

import com.example.comm.Constant;
import com.example.dao.JoinDao;
import com.example.dao.UserDao;
import com.example.domain.entity.Activity;
import com.example.domain.entity.Join;
import com.example.domain.entity.User;
import com.example.domain.enums.Attend;
import com.example.domain.enums.Exist;
import com.example.service.ActivityService;
import com.example.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

/*    @Override
    public JoinsDisplay joinNo(Integer activityId) {

        //      活动
        Activity activity = activityService.findOne(activityId);

        List<Join> joins = joinDao.findAllByActivity_ActivityId(activityId);

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
    }*/

    @Override
    public Join save(Integer userId, Integer activityId, String[] inputDefs, String attend) {
        User user = userDao.findOne(userId);
        Activity activity = activityService.findOne(activityId);

        if (attend.equals("no")){
            String[] labs = new String[activity.getLabelDefs().length];
            for (int i = 0; i < labs.length; i++) {
                labs[i] = "无";
            }
            Join join = new Join(user,activity,labs,Attend.no);
            return joinDao.save(join);
        }
        Join join = new Join(user,activity,inputDefs,Attend.yes);
        return joinDao.save(join);
    }

    @Override
    public Page<Join> findAllByActivity_ActivityId(Integer activityId, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return joinDao.findAllByActivity_ActivityIdAndUser_Exist(activityId, Exist.yes,pageable);
    }

    @Transactional
    @Override
    public void delete(Integer[] joinIds) {
        for (int i = 0; i < joinIds.length; i ++){
            joinDao.delete(joinIds[i]);
        }
    }
}
