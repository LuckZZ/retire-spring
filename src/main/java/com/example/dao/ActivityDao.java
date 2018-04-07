package com.example.dao;

import com.example.domain.enums.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.domain.entity.Activity;

import java.util.List;

public interface ActivityDao extends JpaRepository<Activity,Integer> {

    List<Activity> findAllByActivityStatus(ActivityStatus activityStatus);

    List<Activity> findAllByActivityStatusNot(ActivityStatus activityStatus);

}
