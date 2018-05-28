package com.example.dao;

import com.example.domain.entity.Join;
import com.example.domain.enums.Exist;
import com.example.domain.enums.JoinStatus;
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
    boolean existsByActivity_ActivityIdAndUser_UserIdAndJoinStatus(Integer activityId, Integer userId, JoinStatus joinStatus);

    /*在userIds集合中查询符合条件个数，可以用于判断userIds大小是否等于查询的个数*/
    long countByActivity_ActivityIdAndUser_UserIdInAndJoinStatus(Integer activityId, Integer[] userIds, JoinStatus joinStatus);

    /*根据joinId和groupId，查看Join是否存在此条数据*/
//    boolean existsByJoinIdAndUser_Group_GroupIdAndJoinStatus(Integer joinId, Integer groupId, JoinStatus joinStatus);

    /*在joinIds集合中查询符合条件个数，可以用于判断joinIds大小是否等于查询的个数*/
    long countByJoinIdInAndUser_Group_GroupIdAndJoinStatus(Integer[] joinIds, Integer groupId, JoinStatus joinStatus);

    /*查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndJoinStatus(Integer activityId, Exist exist, JoinStatus joinStatus, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndJoinStatus(Integer activityId, Exist exist, JoinStatus joinStatus);

    /*根据工号查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNumAndJoinStatus(Integer activityId, Exist exist, String jobNum, JoinStatus joinStatus, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNumAndJoinStatus(Integer activityId, Exist exist, String jobNum, JoinStatus joinStatus);

    /*根据姓名查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_NameAndJoinStatus(Integer activityId, Exist exist, String name, JoinStatus joinStatus, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_NameAndJoinStatus(Integer activityId, Exist exist, String name, JoinStatus joinStatus);

    /*根据工号、组Id查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNumAndUser_Group_GroupIdAndJoinStatus(Integer activityId, Exist exist, String jobNum, Integer groupId, JoinStatus joinStatus, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNumAndUser_Group_GroupIdAndJoinStatus(Integer activityId, Exist exist, String jobNum, Integer groupId, JoinStatus joinStatus);

    /*根据姓名、组Id查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_NameAndUser_Group_GroupIdAndJoinStatus(Integer activityId, Exist exist, String name, Integer groupId, JoinStatus joinStatus, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_NameAndUser_Group_GroupIdAndJoinStatus(Integer activityId, Exist exist, String name, Integer groupId, JoinStatus joinStatus);

    /*根据活动Id查询已报活动人数*/
    long countByActivity_ActivityIdAndUser_ExistAndJoinStatus(Integer activityId, Exist exist, JoinStatus joinStatus);

    long countByUser_Group_GroupIdAndActivity_ActivityIdAndUser_ExistAndJoinStatus(Integer groupId, Integer activityId, Exist exist, JoinStatus joinStatus);

    /*根据活动id，用户id，活动状态查询*/
    List<Join> findAllByActivity_ActivityIdAndUser_UserIdAndJoinStatus(Integer activityId, Integer userId, JoinStatus joinStatus);

    /*根据activityId删除*/
    void deleteAllByActivity_ActivityId(Integer activityId);

    /*根据activityId和userId删除*/
    void deleteAllByActivity_ActivityIdAndUser_UserId(Integer activityId, Integer userId);

    /*根据userId删除*/
    void deleteAllByUser_UserId(Integer userId);

}
