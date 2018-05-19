package com.example.dao;

import com.example.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface DepartmentDao extends JpaRepository<Department,Integer> {
}
