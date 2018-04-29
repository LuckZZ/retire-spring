package com.example.service;

import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService extends BaseCrudService<User,Integer>{

    boolean existsByJobNum(String jobNum);

    void delete(Integer[] userIds);

    boolean updateExceptId(User user);

    int updateGroupByUseId(Integer groupId, Integer userId);

    boolean updateExistYes(Integer userId);

    boolean updateExistNO(Integer userId, String passTime);

    Page<User> findAllByGroup_GroupId(Integer groupId, Integer page);

    /*根据工号查询用户*/
    Page<User> findAllByJobNum(String jobNum, Integer page);

    List<User> findAllByJobNum(String jobNum);

    /*根据用户名查询用户*/
    Page<User> findAllByName(String name, Integer page);

    List<User> findAllByName(String name);

    /*根据userSearchForm查询用户*/
    Page<User> findAllUserCriteria(Integer page, UserSearchForm userSearchForm);

    List<User> findAllUserCriteria(UserSearchForm userSearchForm);

    /*查询未报名用户*/
    Page<User> findAllNoJoinCriteria(Integer activityId, UserSearchForm userSearchForm, Integer page);

    List<User> findAllNoJoinCriteria(Integer activityId, UserSearchForm userSearchForm);

    /*根据工号查询未报名用户*/
    Page<User> findAllNoJoinByJobNum(Integer activityId, String jobNum, Integer page);

    List<User> findAllNoJoinByJobNum(Integer activityId, String jobNum);

    /*根据用户名查询未报名用户*/
    Page<User> findAllNoJoinByName(Integer activityId, String name, Integer page);

    List<User> findAllNoJoinByName(Integer activityId, String name);

    /*根据userIds查询用户*/
    List<User> findAllByUserIds(Integer[] userIds);

}
