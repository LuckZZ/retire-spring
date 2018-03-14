package com.example.dao;

import com.example.domain.entity.AgeRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgeRangeDao extends JpaRepository<AgeRange,Integer> {

}
