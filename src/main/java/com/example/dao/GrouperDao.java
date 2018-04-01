package com.example.dao;

import com.example.domain.entity.Grouper;
import com.example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrouperDao extends JpaRepository<Grouper, Integer> {

    @Modifying(clearAutomatically=true)
    @Query("delete from Grouper where user=:user")
    void deleteByUser(@Param("user") User user);

}
