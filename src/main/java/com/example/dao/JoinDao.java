package com.example.dao;

import com.example.domain.entity.Join;
import com.example.domain.enums.Exist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface JoinDao extends JpaRepository<Join,Integer>,JpaSpecificationExecutor<Join> {

    /*根据activityId和userId，查看Join是否存在此条数据*/
    boolean existsByActivity_ActivityIdAndUser_UserId(Integer activityId, Integer userId);

    /*根据joinId和groupId，查看Join是否存在此条数据*/
    boolean existsByJoinIdAndUser_Group_GroupId(Integer joinId, Integer groupId);

    /*查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_Exist(Integer activityId, Exist exist, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_Exist(Integer activityId, Exist exist);

    /*根据工号查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNum(Integer activityId, Exist exist, String jobNum, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNum(Integer activityId, Exist exist, String jobNum);

    /*根据姓名查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_Name(Integer activityId, Exist exist, String name, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_Name(Integer activityId, Exist exist, String name);

    /*根据工号、组Id查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNumAndUser_Group_GroupId(Integer activityId, Exist exist, String jobNum, Integer groupId, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNumAndUser_Group_GroupId(Integer activityId, Exist exist, String jobNum, Integer groupId);

    /*根据姓名、组Id查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_NameAndUser_Group_GroupId(Integer activityId, Exist exist, String name, Integer groupId, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_NameAndUser_Group_GroupId(Integer activityId, Exist exist, String name, Integer groupId);

    /*根据活动Id查询已报活动人数*/
    long countByActivity_ActivityIdAndUser_Exist(Integer activityId, Exist exist);

    long countByUser_Group_GroupIdAndActivity_ActivityIdAndUser_Exist(Integer groupId, Integer activityId, Exist exist);

    /*根据activityId删除*/
    void deleteAllByActivity_ActivityId(Integer activityId);

    /*根据userId删除*/
    void deleteAllByUser_UserId(Integer userId);

}
