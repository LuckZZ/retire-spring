package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.domain.entity.Activity;

public interface ActivityDao extends JpaRepository<Activity,Integer> {
}
