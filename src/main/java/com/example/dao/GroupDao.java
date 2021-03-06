package com.example.dao;

import com.example.domain.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface GroupDao extends JpaRepository<Group,Integer> {

    boolean existsByGroupName(String groupName);

    Group findByGroupName(String groupName);

    @Modifying(clearAutomatically=true)
    @Query("update Group set groupName=:groupName where groupId=:groupId")
    int updateGroupName(@Param("groupName") String groupName, @Param("groupId") Integer groupId);

    Page<Group> findAllByGroupNameContaining(String groupName, Pageable pageable);

}
