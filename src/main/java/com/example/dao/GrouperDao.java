package com.example.dao;

import com.example.domain.entity.Grouper;
import com.example.domain.entity.User;
import com.example.domain.enums.CanLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrouperDao extends JpaRepository<Grouper, Integer> {

    @Modifying(clearAutomatically=true)
    @Query("delete from Grouper where user=:user")
    void deleteByUser(@Param("user") User user);

    @Modifying(clearAutomatically=true)
    @Query("update Grouper set canLogin=:canLogin where grouperId=:grouperId")
    int updateCanLogin(@Param("canLogin") CanLogin canLogin, @Param("grouperId") Integer grouperId);

    @Modifying(clearAutomatically=true)
    @Query("update Grouper set password=:password where grouperId=:grouperId")
    int updatePwd(@Param("password") String password, @Param("grouperId") Integer grouperId);

    Grouper findByUser(User user);

}
