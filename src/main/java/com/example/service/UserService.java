package com.example.service;

import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService extends BaseCrudService<User,Integer>{

    boolean existsByJobNum(String jobNum);

    void delete(Integer[] userIds);

    boolean updateExceptId(User user);

    List<User> findAllByJobNum(String jobNum);

    List<User> findAllByName(String name);

    int updateGroupByUseId(Integer groupId, Integer userId);

    Page<User> findAllByJobNum(String jobNum, Integer page);

    Page<User> findAllByName(String name, Integer page);

    boolean updateExistYes(Integer userId);

    boolean updateExistNO(Integer userId, String passTime);

    Page<User> findAllUserCriteria(Integer page, UserSearchForm userSearchForm);

    List<User> findAllUserCriteria(UserSearchForm userSearchForm);

    Page<User> findAllByGroup_GroupId(Integer groupId, Integer page);

    Page<User> findAllNoJoin(Integer activityId, Integer page);

    List<User> findAllUserNoJoinCriteria(Integer activityId, UserSearchForm userSearchForm);

    Page<User> findAllNoJoinByJobNum(Integer activityId, String jobNum, Integer page);

    Page<User> findAllNoJoinByName(Integer activityId, String name, Integer page);

    Page<User> findAllNoJoinCriteria(Integer page, UserSearchForm userSearchForm, Integer activityId);
}
