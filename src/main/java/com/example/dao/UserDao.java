package com.example.dao;

import com.example.domain.entity.Group;
import com.example.domain.entity.User;
import com.example.domain.enums.Exist;
import com.example.domain.enums.Rank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    boolean existsByJobNum(String jobNum);

    List<User> findAllByJobNum(String jobNum);

    List<User> findAllByName(String name);

    @Modifying(clearAutomatically=true)
    @Query("update User set rank=:rank where userId=:userId")
    int updateRank(@Param("rank") Rank rank, @Param("userId") Integer userId);

    @Modifying(clearAutomatically=true)
    @Query("update User set exist=:exist, passTime=:passTime where userId=:userId")
    int updateExist(@Param("exist") Exist exist, @Param("passTime") String passTime, @Param("userId") Integer userId);

    @Modifying(clearAutomatically=true)
    @Query(value = "update tb_user set group_id=?1 where group_id=?2", nativeQuery = true)
    int updateGroup(Integer newGroupId, Integer oldGroupId);

    @Modifying(clearAutomatically=true)
    @Query(value = "update tb_user set group_id=?1 where user_id=?2", nativeQuery = true)
    int updateGroupByUseId(Integer groupId, Integer userId);

    /*根据存在类型统计人数*/
    long countByExist(Exist exist);

    long countByGroup_GroupIdAndExist(Integer groupId, Exist exist);

    /*根据组号、工号查询用户*/
    Page<User> findAllByGroup_GroupIdAndJobNum(Integer groupId, String jobNum, Pageable pageable);

    /*根据组号、姓名查询用户*/
    Page<User> findAllByGroup_GroupIdAndName(Integer groupId, String name, Pageable pageable);

    /*根据组号查询用户*/
    Page<User> findAllByGroup_GroupId(Integer groupId, Pageable pageable);

    /*根据工号查询用户*/
    Page<User> findAllByJobNum(String jobNum, Pageable pageable);

    /*根据姓名查询用户*/
    Page<User> findAllByName(String name, Pageable pageable);

    /*根据工号查询未报名用户*/
    @Query("select u from User u where u.exist=:exist and u not in (select j.user from Join j where j.activity.activityId=:activityId) and u.jobNum=:jobNum")
    Page<User> findAllNoJoinByJobNum(@Param("exist")Exist exist, @Param("activityId")Integer activityId, @Param("jobNum")String jobNum, Pageable pageable);

    @Query("select u from User u where u.exist=:exist and u not in (select j.user from Join j where j.activity.activityId=:activityId) and u.jobNum=:jobNum")
    List<User> findAllNoJoinByJobNum(@Param("exist")Exist exist, @Param("activityId")Integer activityId, @Param("jobNum")String jobNum);

    /*根据用户名查询未报名用户*/
    @Query("select u from User u where u.exist=:exist and u not in (select j.user from Join j where j.activity.activityId=:activityId) and u.name=:name")
    Page<User> findAllNoJoinByName(@Param("exist")Exist exist, @Param("activityId")Integer activityId, @Param("name")String name, Pageable pageable);

    @Query("select u from User u where u.exist=:exist and u not in (select j.user from Join j where j.activity.activityId=:activityId) and u.jobNum=:jobNum")
    List<User> findAllNoJoinByName(@Param("exist")Exist exist, @Param("activityId")Integer activityId, @Param("jobNum")String jobNum);
}
