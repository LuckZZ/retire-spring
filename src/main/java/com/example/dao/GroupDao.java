package com.example.dao;

import com.example.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupDao extends JpaRepository<Group,Integer> {

    boolean existsByGroupName(String groupName);

    List<Group> findAllByOrderByGroupId();

}
