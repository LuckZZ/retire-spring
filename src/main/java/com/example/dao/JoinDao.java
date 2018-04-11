package com.example.dao;

import com.example.domain.entity.Activity;
import com.example.domain.entity.Join;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinDao extends JpaRepository<Join,Integer> {

    List<Join> findAllByActivity_ActivityId(Integer activityId);

    long countByActivity_ActivityId(Integer activityId);

    void deleteAllByActivity_ActivityId(Integer activityId);
}
