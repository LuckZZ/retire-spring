package com.example.dao;

import com.example.domain.entity.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DutyDao extends JpaRepository<Duty,Integer> {
}
