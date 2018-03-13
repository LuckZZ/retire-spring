package com.example.dao;

import com.example.domain.entity.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoDao extends JpaRepository<Demo, Long> {

}
