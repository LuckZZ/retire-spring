package com.example.dao;

import com.example.domain.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface NationDao extends JpaRepository<Nation,Integer> {
}
