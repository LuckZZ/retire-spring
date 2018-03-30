package com.example.dao;

import com.example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    boolean existsByJobNum(String jobNum);

    List<User> findAllByJobNum(String jobNum);

    List<User> findAllByName(String name);
}
