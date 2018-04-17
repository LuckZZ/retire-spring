package com.example.serviceImpl;
import com.example.comm.Constant;
import com.example.dao.GroupDao;
import com.example.dao.UserDao;
import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.Group;
import com.example.domain.entity.User;
import com.example.domain.enums.Exist;
import com.example.domain.enums.Rank;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Service
public class UserServiceImpl extends BaseCrudServiceImpl<User, Integer, UserDao> implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public boolean existsByJobNum(String jobNum) {
        return userDao.existsByJobNum(jobNum);
    }

    @Transactional
    @Override
    public void delete(Integer[] userIds) {
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
        Page<User> datas = userDao.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if ("-1"!=userSearchForm.getGroup()){
                    list.add(cb.equal(root.join("group").get("groupId").as(Integer.class), Integer.parseInt(userSearchForm.getGroup())));
                }
                if ("-1"!=userSearchForm.getGender()){
                    list.add(cb.equal(root.join("gender").get("name").as(String.class), userSearchForm.getGroup()));
                }
                if ("-1"!=userSearchForm.getRank()){

                }
                if ("-1"!=userSearchForm.getDuty()){

                }
                if ("-1"!=userSearchForm.getDepartment()){

                }
                if ("-1"!=userSearchForm.getExist()){

                }
                if ("-1"!=userSearchForm.getPolitics()){

                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, pageable);
        return datas;
    }

}
