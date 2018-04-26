package com.example.serviceImpl;

import com.example.comm.Constant;
import com.example.dao.JoinDao;
import com.example.dao.UserDao;
import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.Activity;
import com.example.domain.entity.Join;
import com.example.domain.entity.User;
import com.example.domain.enums.Attend;
import com.example.domain.enums.Exist;
import com.example.domain.enums.Gender;
import com.example.domain.enums.Rank;
import com.example.service.ActivityService;
import com.example.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
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

    @Override
    public Join save(Integer userId, Integer activityId, String[] inputDefs, String attend) {
        User user = userDao.findOne(userId);
        Activity activity = activityService.findOne(activityId);

//        如果不参加
/*        if (!isAttend(attend)){
            String[] labs = new String[activity.getLabelDefs().length];
            for (int i = 0; i < labs.length; i++) {
                labs[i] = "无";
            }
            Join join = new Join(user,activity,labs,Attend.no);
            return joinDao.save(join);
        }
//        如果参加
        Join join = new Join(user,activity,inputDefs,Attend.yes);
        return joinDao.save(join);*/
        return null;
    }

    @Transactional
    @Override
    public void delete(Integer[] joinIds) {
        for (int i = 0; i < joinIds.length; i ++){
            joinDao.delete(joinIds[i]);
        }
    }

    @Override
    public Page<Join> findAllByActivity_ActivityId(Integer activityId, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return joinDao.findAllByActivity_ActivityIdAndUser_Exist(activityId, Exist.yes,pageable);
    }

    @Override
    public Page<Join> findAllByActivity_ActivityIdAndUser_JobNum(Integer activityId, String jobNum, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return joinDao.findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNum(activityId, Exist.yes, jobNum, pageable);
    }

    @Override
    public Page<Join> findAllByActivity_ActivityIdAndUser_Name(Integer activityId, String name, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return joinDao.findAllByActivity_ActivityIdAndUser_ExistAndUser_Name(activityId, Exist.yes, name, pageable);
    }

    @Override
    public Page<Join> findAllUserCriteria(Integer activityId, String[] inputDefs, String attend, UserSearchForm userSearchForm, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return joinDao.findAll(joinSpecification(activityId, inputDefs, attend, userSearchForm), pageable);
    }

    private Specification<Join> joinSpecification(Integer activityId, String[] inputDefs, String attend, UserSearchForm userSearchForm){

        Specification<Join> specification = new Specification<Join>() {
            @Override
            public Predicate toPredicate(Root<Join> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
//                根据用户属性筛选
                if (!"-1".equals(userSearchForm.getGroup())){
                    list.add(cb.equal(root.join("user").join("group").get("groupId").as(Integer.class), Integer.parseInt(userSearchForm.getGroup())));
                }
                if (!"-1".equals(userSearchForm.getGender())){
                    list.add(cb.equal(root.join("user").get("gender").as(Gender.class), Gender.valueOf(userSearchForm.getGender())));
                }
                if (!"-1".equals(userSearchForm.getRank())){
                    list.add(cb.equal(root.join("user").get("rank").as(Rank.class), Rank.valueOf(userSearchForm.getRank())));
                }
                if (!"-1".equals(userSearchForm.getDuty())){
                    list.add(cb.equal(root.join("user").join("duty").get("dutyId").as(Integer.class), Integer.parseInt(userSearchForm.getDuty())));
                }
                if (!"-1".equals(userSearchForm.getDepartment())){
                    list.add(cb.equal(root.join("user").join("department").get("departmentId").as(Integer.class), Integer.parseInt(userSearchForm.getDepartment())));
                }
                if (!"-1".equals(userSearchForm.getExist())){
                    list.add(cb.equal(root.join("user").get("exist").as(Exist.class), Exist.valueOf(userSearchForm.getExist())));
                }
                if (!"-1".equals(userSearchForm.getPolitics())){
                    list.add(cb.equal(root.join("user").join("politics").get("politicsId").as(Integer.class), Integer.parseInt(userSearchForm.getPolitics())));
                }
//                根据活动id筛选
                list.add(cb.equal(root.join("activity").get("activityId").as(Integer.class), activityId));
//                根据已报属性筛选
                if (!"-1".equals(attend)){
                    if (isAttend(attend)){
//                        参加
                        list.add(cb.equal(root.get("attend").as(Attend.class), Attend.yes));
           /*             String[] str = {"地点一"};
                        for (int i = 0; i < inputDefs.length; i++) {
                            list.add(cb.equal(root.join("activity").get("inputDefs").as(String[].class), str));
                        }*/

                        ListJoin<Join, String> join = root.join(root.getModel().getList("string",String.class));

                    }else if (!isAttend(attend)){
//                    不参加
                        list.add(cb.equal(root.get("attend").as(Attend.class), Attend.no));
                    }
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return specification;
    }

    /**
     *
     * @param attend
     * @return true:参数  false:不参加
     */
    private boolean isAttend(String attend){
        if (attend.equals("no")){
            return false;
        }
        return true;
    }
}
