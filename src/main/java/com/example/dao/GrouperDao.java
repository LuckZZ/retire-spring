package com.example.dao;

import com.example.domain.entity.Admin;
import com.example.domain.entity.Grouper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrouperDao extends JpaRepository<Grouper, Integer> {

}
