package com.example.serviceImpl;
import com.example.comm.Constant;
import com.example.dao.GrouperDao;
import com.example.dao.JoinDao;
import com.example.dao.UserDao;
import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.Join;
import com.example.domain.entity.Politics;
import com.example.domain.entity.User;
import com.example.domain.enums.Exist;
import com.example.domain.enums.Gender;
import com.example.domain.enums.JoinStatus;
import com.example.domain.enums.Rank;
import com.example.service.UserService;
import com.example.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Service
public class UserServiceImpl extends BaseCrudServiceImpl<User, Integer, UserDao> implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private GrouperDao grouperDao;

    @Autowired
    private JoinDao joinDao;

    @Value("${file.pictures.url}")
    private String filePicturesUrl;

    @Override
    public boolean existsByJobNum(String jobNum) {
        return userDao.existsByJobNum(jobNum);
    }

    @Override
    public boolean existsByUserIdAndGroupId(Integer userId, Integer groupId) {
        return userDao.existsByUserIdAndGroup_GroupId(userId, groupId);
    }

    @Override
    public boolean existsByUserIdAndGroupId(Integer[] userIds, Integer groupId) {
        long count = userDao.countByUserIdInAndGroup_GroupId(userIds, groupId);
        if (count == userIds.length){
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public int updateImg(String imgUrl, Integer userId) {
        return userDao.updateImg(imgUrl, userId);
    }

    @Transactional
    @Override
    public void delete(Integer[] userIds) {
//       删除组长表
        for (int i = 0; i < userIds.length; i ++){
            grouperDao.deleteByUserId(userIds[i]);
        }
        //       删除报名表
        for (int i = 0; i < userIds.length; i ++){
            joinDao.deleteAllByUser_UserId(userIds[i]);
        }
        //        删除图片
        for (int i = 0; i < userIds.length; i ++){
//            userDao.delete(userIds[i]);
            User user = userDao.findOne(userIds[i]);
            FileUtils.deleteFile(filePicturesUrl+user.getImgUrl());
        }
//        删除组员表
        for (int i = 0; i < userIds.length; i ++){
            userDao.delete(userIds[i]);
        }
    }

    @Transactional
    @Override
    public boolean updateExceptId(User user){
        if (user.getUserId() == null){
            return false;
        }
//        用户存在的状态不变
        User oldUser = userDao.findOne(user.getUserId());
        user.setExist(oldUser.getExist());
//        用户身份不变
        user.setRank(oldUser.getRank());

//        保存，由于userId不变，执行的是update语句
        userDao.save(user);
        return true;
    }

    @Override
    public List<User> findAllByJobNum(String jobNum) {
        return userDao.findAllByJobNum(jobNum);
    }

    @Override
    public List<User> findAllByName(String name) {
        return userDao.findAllByName(name);
    }

    @Transactional
    @Override
    public int updateGroupByUseId(Integer groupId, Integer userId) {
        return userDao.updateGroupByUseId(groupId,userId);
    }

    @Override
    public Page<User> findAllByJobNum(String jobNum, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return userDao.findAllByJobNum(jobNum,pageable);
    }

    @Override
    public Page<User> findAllByName(String name, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return userDao.findAllByName(name,pageable);
    }

    @Transactional
    @Override
    public boolean updateExistYes(Integer userId) {
        userDao.updateExist(Exist.yes, null, userId);
        return true;
    }

    @Transactional
    @Override
    public boolean updateExistNO(Integer userId, String passTime) {
        userDao.updateExist(Exist.no, passTime, userId);
        return true;
    }

    @Transactional
    @Override
    public User save(User user) {
//        设置存在
        user.setExist(Exist.yes);
//        默认组员
        user.setRank(Rank.user);

        return userDao.save(user);
    }

    @Override
    public Page<User> findAllUserCriteria(Integer page, UserSearchForm userSearchForm) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<User> datas = userDao.findAll(userSpecification(userSearchForm), pageable);
        return datas;
    }

    @Override
    public List<User> findAllUserCriteria(UserSearchForm userSearchForm) {
        return userDao.findAll(userSpecification(userSearchForm));
    }

    @Override
    public Page<User> findAllByGroupId(Integer groupId, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<User> datas = userDao.findAllByGroup_GroupIdAndExist(groupId, Exist.yes, pageable);
        return datas;
    }

    @Override
    public Page<User> findAllByGroupIdAndJobNum(Integer groupId, String jobNum, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<User> datas = userDao.findAllByGroup_GroupIdAndJobNum(groupId,jobNum,pageable);
        return datas;
    }

    @Override
    public Page<User> findAllByByGroupIdAndName(Integer groupId, String name, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<User> datas = userDao.findAllByGroup_GroupIdAndName(groupId,name,pageable);
        return datas;
    }

    @Override
    public List<User> findAllNoJoinCriteria(Integer activityId, UserSearchForm userSearchForm) {
        return userDao.findAll(noJionUserSpecification(userSearchForm, getJoinUserIds(activityId)));
    }

    @Override
    public Page<User> findAllNoJoinByJobNum(Integer activityId, String jobNum, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return userDao.findAllNoJoinByJobNum(Exist.yes, activityId, jobNum, JoinStatus.draft, pageable);
    }

    @Override
    public List<User> findAllNoJoinByJobNum(Integer activityId, String jobNum) {
        return userDao.findAllNoJoinByJobNum(Exist.yes, activityId, jobNum, JoinStatus.draft);
    }

    @Override
    public Page<User> findAllNoJoinByName(Integer activityId, String name, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return userDao.findAllNoJoinByName(Exist.yes, activityId, name, JoinStatus.draft, pageable);
    }

    @Override
    public List<User> findAllNoJoinByName(Integer activityId, String name) {
        return userDao.findAllNoJoinByName(Exist.yes, activityId, name, JoinStatus.draft);
    }

    @Override
    public Page<User> findAllNoJoinByJobNumWithGroupId(Integer groupId, Integer activityId, String jobNum, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return userDao.findAllNoJoinByJobNumWithGroupId(Exist.yes, groupId, activityId, jobNum, JoinStatus.draft, pageable);
    }

    @Override
    public List<User> findAllNoJoinByJobNumWithGroupId(Integer groupId, Integer activityId, String jobNum) {
        return userDao.findAllNoJoinByJobNumWithGroupId(Exist.yes, groupId, activityId, jobNum, JoinStatus.draft);
    }

    @Override
    public Page<User> findAllNoJoinByNameWithGroupId(Integer groupId, Integer activityId, String name, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return userDao.findAllNoJoinByNameWithGroupId(Exist.yes, groupId, activityId, name, JoinStatus.draft, pageable);
    }

    @Override
    public List<User> findAllNoJoinByNameWithGroupId(Integer groupId, Integer activityId, String name) {
        return userDao.findAllNoJoinByNameWithGroupId(Exist.yes, groupId, activityId, name, JoinStatus.draft);
    }

    @Override
    public List<User> findAllByUserIds(Integer[] userIds) {
        Iterable<Integer> it = Arrays.asList(userIds);
        return userDao.findAll(it);
    }

    @Override
    public Page<User> findAllNoJoinCriteria(Integer activityId, UserSearchForm userSearchForm, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<User> userPage = userDao.findAll(noJionUserSpecification(userSearchForm, getJoinUserIds(activityId)), pageable);
        return userPage;
    }

    private Specification userSpecification(UserSearchForm userSearchForm){
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!"-1".equals(userSearchForm.getGroup())){
                    list.add(cb.equal(root.join("group").get("groupId").as(Integer.class), Integer.parseInt(userSearchForm.getGroup())));
                }
                if (!"-1".equals(userSearchForm.getGender())){
                    list.add(cb.equal(root.get("gender").as(Gender.class), Gender.valueOf(userSearchForm.getGender())));
                }
                if (!"-1".equals(userSearchForm.getRank())){
                    list.add(cb.equal(root.get("rank").as(Rank.class), Rank.valueOf(userSearchForm.getRank())));
                }
                if (!"-1".equals(userSearchForm.getDuty())){
                    list.add(cb.equal(root.join("duty").get("dutyId").as(Integer.class), Integer.parseInt(userSearchForm.getDuty())));
                }
                if (!"-1".equals(userSearchForm.getDepartment())){
                    list.add(cb.equal(root.join("department").get("departmentId").as(Integer.class), Integer.parseInt(userSearchForm.getDepartment())));
                }
                if (!"-1".equals(userSearchForm.getExist())){
                    list.add(cb.equal(root.get("exist").as(Exist.class), Exist.valueOf(userSearchForm.getExist())));
                }
                if (!"-1".equals(userSearchForm.getPolitics())){
                    list.add(cb.equal(root.join("politics").get("politicsId").as(Politics.class), Integer.parseInt(userSearchForm.getPolitics())));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return specification;
    }

    private Specification noJionUserSpecification(UserSearchForm userSearchForm, List<Integer> joinUserIds){

        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!"-1".equals(userSearchForm.getGroup())){
                    list.add(cb.equal(root.join("group").get("groupId").as(Integer.class), Integer.parseInt(userSearchForm.getGroup())));
                }
                if (!"-1".equals(userSearchForm.getGender())){
                    list.add(cb.equal(root.get("gender").as(Gender.class), Gender.valueOf(userSearchForm.getGender())));
                }
                if (!"-1".equals(userSearchForm.getRank())){
                    list.add(cb.equal(root.get("rank").as(Rank.class), Rank.valueOf(userSearchForm.getRank())));
                }
                if (!"-1".equals(userSearchForm.getDuty())){
                    list.add(cb.equal(root.join("duty").get("dutyId").as(Integer.class), Integer.parseInt(userSearchForm.getDuty())));
                }
                if (!"-1".equals(userSearchForm.getDepartment())){
                    list.add(cb.equal(root.join("department").get("departmentId").as(Integer.class), Integer.parseInt(userSearchForm.getDepartment())));
                }
                if (!"-1".equals(userSearchForm.getExist())){
                    list.add(cb.equal(root.get("exist").as(Exist.class), Exist.valueOf(userSearchForm.getExist())));
                }
                if (!"-1".equals(userSearchForm.getPolitics())){
                    list.add(cb.equal(root.join("politics").get("politicsId").as(Integer.class), Integer.parseInt(userSearchForm.getPolitics())));
                }
                if (joinUserIds!=null && joinUserIds.size()!=0){
                    list.add(cb.not(root.get("userId").in(joinUserIds)));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return specification;
    }

    private List<Integer> getJoinUserIds(Integer activityId){
        List<Join> joins = joinDao.findAllByActivity_ActivityIdAndUser_ExistAndJoinStatus(activityId, Exist.yes, JoinStatus.ultima);

        List<Integer> joinUserIds = new ArrayList<>();
        for (Join join : joins) {
            joinUserIds.add(join.getUser().getUserId());
        }
        return joinUserIds;
    }
}
