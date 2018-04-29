package com.example.dao;

import com.example.domain.entity.Join;
import com.example.domain.enums.Exist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JoinDao extends JpaRepository<Join,Integer>,JpaSpecificationExecutor<Join> {

    boolean existsByActivity_ActivityIdAndUser_UserId(Integer activityId, Integer userId);

    /*查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_Exist(Integer activityId, Exist exist, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_Exist(Integer activityId, Exist exist);

    /*根据工号查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNum(Integer activityId, Exist exist, String jobNum, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_JobNum(Integer activityId, Exist exist, String jobNum);

    /*根据姓名查询报名用户*/
    Page<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_Name(Integer activityId, Exist exist, String name, Pageable pageable);

    List<Join> findAllByActivity_ActivityIdAndUser_ExistAndUser_Name(Integer activityId, Exist exist, String name);

    long countByActivity_ActivityIdAndUser_Exist(Integer activityId, Exist exist);

    void deleteAllByActivity_ActivityId(Integer activityId);

}
