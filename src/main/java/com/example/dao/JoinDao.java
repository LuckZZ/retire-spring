package com.example.dao;

import com.example.domain.entity.Join;
import com.example.domain.enums.Exist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinDao extends JpaRepository<Join,Integer> {

    List<Join> findAllByActivity_ActivityId(Integer activityId);

    Page<Join> findAllByActivity_ActivityIdAndUser_Exist(Integer activityId, Exist exist, Pageable pageable);

    long countByActivity_ActivityIdAndUser_Exist(Integer activityId, Exist exist);

    void deleteAllByActivity_ActivityId(Integer activityId);
}
