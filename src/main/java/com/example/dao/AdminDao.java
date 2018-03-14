package com.example.dao;

import com.example.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AdminDao extends JpaRepository<Admin, Integer> {

}
