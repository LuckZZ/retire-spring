package com.example.dao;

import com.example.domain.enums.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.domain.entity.Activity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityDao extends JpaRepository<Activity,Integer> {

    List<Activity> findAllByActivityStatus(ActivityStatus activityStatus);

    List<Activity> findAllByActivityStatusNot(ActivityStatus activityStatus);

    boolean existsByActivityName(String activityName);

    @Modifying(clearAutomatically=true)
    @Query("update Activity set activityStatus=:activityStatus where activityId=:activityId")
    int updateActivityStatus(@Param("activityStatus") ActivityStatus activityStatus, @Param("activityId") Integer activityId);

}
