package com.example.dao;

import com.example.domain.enums.ActivityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.domain.entity.Activity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDao extends JpaRepository<Activity,Integer> {

    Page<Activity> findAllByActivityStatus(ActivityStatus activityStatus, Pageable pageable);

    Page<Activity> findAllByActivityStatusNot(ActivityStatus activityStatus, Pageable pageable);

    Page<Activity> findAllByActivityStatusAndActivityName(ActivityStatus activityStatus, String activityName, Pageable pageable);

    Page<Activity> findAllByActivityStatusNotAndActivityName(ActivityStatus activityStatus, String activityName, Pageable pageable);

    boolean existsByActivityName(String activityName);

    @Modifying(clearAutomatically=true)
    @Query("update Activity set activityStatus=:activityStatus where activityId=:activityId")
    int updateActivityStatus(@Param("activityStatus") ActivityStatus activityStatus, @Param("activityId") Integer activityId);

}
