package com.example.dao;

import com.example.domain.entity.ActivityDef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface ActivityDefDao extends JpaRepository<ActivityDef,Integer> {

    @Modifying(clearAutomatically=true)
    @Query(value = "delete from tb_activity_def where activity_id=?1", nativeQuery = true)
    void deleteAllByActivityId(Integer activityId);

}
