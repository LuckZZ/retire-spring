package com.example.service;

import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface UserService extends BaseCrudService<User,Integer>{

    /*是否存在此工号*/
    boolean existsByJobNum(String jobNum);

    /*根据userId和groupId查询是否有对应的数据*/
    boolean existsByUserIdAndGroupId(Integer userId, Integer groupId);

    /*根据userIds和groupId查询是否有对应的数据*/
    boolean existsByUserIdAndGroupId(Integer[] userIds, Integer groupId);

    /*根据userId修改图片*/
    int updateImg(String imgUrl, Integer userId);

    void delete(Integer[] userIds);

    boolean updateExceptId(User user);

    int updateGroupByUseId(Integer groupId, Integer userId);

    boolean updateExistYes(Integer userId);

    boolean updateExistNO(Integer userId, String passTime);

    Page<User> findAllByGroupId(Integer groupId, Integer page);

    /*根据工号查询用户*/
    Page<User> findAllByJobNum(String jobNum, Integer page);

    List<User> findAllByJobNum(String jobNum);

    /*根据用户名查询用户*/
    Page<User> findAllByNameContaining(String name, Integer page);

    List<User> findAllByName(String name);

    /*根据组号、工号查询用户*/
    Page<User> findAllByGroupIdAndJobNum(Integer groupId, String jobNum, Integer page);

    /*根据组号、用户名查询用户*/
    Page<User> findAllByGroupIdAndNameContaining(Integer groupId, String name, Integer page);

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
    Page<User> findAllNoJoinByNameContaining(Integer activityId, String name, Integer page);

    List<User> findAllNoJoinByNameContaining(Integer activityId, String name);

    /*根据组id、工号查询未报名用户*/
    Page<User> findAllNoJoinByJobNumWithGroupId(Integer groupId, Integer activityId, String jobNum, Integer page);

    List<User> findAllNoJoinByJobNumWithGroupId(Integer groupId, Integer activityId, String jobNum);

    /*根据组id、工号查询未报名用户*/
    Page<User> findAllNoJoinByNameContainingWithGroupId(Integer groupId, Integer activityId, String name, Integer page);

    List<User> findAllNoJoinByNameContainingWithGroupId(Integer groupId, Integer activityId, String name);

    /*根据userIds查询用户*/
    List<User> findAllByUserIds(Integer[] userIds);

}
