package com.example.dao;

import com.example.domain.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface NationDao extends JpaRepository<Nation,Integer> {
}
