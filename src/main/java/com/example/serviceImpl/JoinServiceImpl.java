package com.example.serviceImpl;

import com.example.comm.Constant;
import com.example.dao.JoinDao;
import com.example.dao.UserDao;
import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.*;
import com.example.domain.entity.Join;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class JoinServiceImpl extends BaseCrudServiceImpl<Join, Integer, JoinDao> implements JoinService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private JoinDao joinDao;

    @Autowired
    private ActivityService activityService;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public Join save(Integer userId, Integer activityId, String[] inputDefs, String attend) {
        User user = userDao.findOne(userId);
        Activity activity = activityService.findOne(activityId);
        List<ActivityDef> activityDefs = activity.getActivityDefs();
        Iterator<ActivityDef> it = activityDefs.iterator();
//        如果不参加
        if (!isAttend(attend)){
            List<JoinDef> joinDefs = new ArrayList<>();
            for (int i = 0; i < inputDefs.length; i++) {
                ActivityDef activityDef = it.next();
                joinDefs.add(new JoinDef(activityDef,"无"));
            }
            Join join = new Join(user, activity, joinDefs, Attend.no);
            return joinDao.save(join);
        }
//        如果参加
        List<JoinDef> joinDefs = new ArrayList<>();
        for (int i = 0; i < inputDefs.length; i++) {
            ActivityDef activityDef = it.next();
            joinDefs.add(new JoinDef(activityDef, inputDefs[i]));
        }

        Join join = new Join(user, activity, joinDefs,Attend.yes);
        return joinDao.save(join);
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
        List<Integer> joinIdByDef = selectJoinId(activityId, inputDefs);
        return joinDao.findAll(joinSpecification(activityId, attend, userSearchForm, joinIdByDef), pageable);
    }

    /**
     * 根据条件筛选出joinId
     * SELECT join_id FROM tb_join_def WHERE (activity_def_id=2 AND input='时间一') OR (activity_def_id=3 AND input='地点一') GROUP BY join_id HAVING COUNT(*) = 2
     * @param activityId
     * @param inputDefs
     * @return
     */
    private List<Integer> selectJoinId(Integer activityId, String[] inputDefs){
        Activity activity = activityService.findOne(activityId);
        List<ActivityDef> activityDefs = activity.getActivityDefs();
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT join_id FROM tb_join_def WHERE ");
        for (int i = 0; i < inputDefs.length; i ++){
//                  条件
            if (!"-1".equals(inputDefs[i])){
                sql.append(" (activity_def_id="+activityDefs.get(i).getId()+" AND input='"+inputDefs[i]+"') ");
            }else {
                sql.append(" (activity_def_id="+activityDefs.get(i).getId()+" AND input LIKE '%%') ");
            }
//                    如果不是到最后一个
            if (i != (inputDefs.length-1)){
                sql.append(" OR ");
            }
        }
        sql.append(" GROUP BY join_id HAVING COUNT(*) = "+inputDefs.length+" ");
//        创建原生sql查询query示例
        Query query = em.createNativeQuery(sql.toString());
        List<Integer> objectList = query.getResultList();
        em.close();
        return objectList;
    }

   private Specification<Join> joinSpecification(Integer activityId, String attend, UserSearchForm userSearchForm, List<Integer> joinIdByDef){

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
                        list.add(root.get("joinId").in(joinIdByDef));
                    }else if (!isAttend(attend)){
//                    不参加
                        list.add(cb.equal(root.get("attend").as(Attend.class), Attend.no));
                    }
                }else {
                    list.add(root.get("joinId").in(joinIdByDef));
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
