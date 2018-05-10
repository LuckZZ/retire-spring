package com.example.dao;

import com.example.domain.entity.Grouper;
import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Exist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrouperDao extends JpaRepository<Grouper, Integer> {

    @Modifying(clearAutomatically=true)
    @Query("delete from Grouper where user.userId=:userId")
    void deleteByUserId(@Param("userId") Integer userId);

    /*修改登录权限*/
    @Modifying(clearAutomatically=true)
    @Query("update Grouper set canLogin=:canLogin where grouperId=:grouperId")
    int updateCanLogin(@Param("canLogin") CanLogin canLogin, @Param("grouperId") Integer grouperId);

    /*修改密码*/
    @Modifying(clearAutomatically=true)
    @Query("update Grouper set password=:password where grouperId=:grouperId")
    int updatePassword(@Param("password") String password, @Param("grouperId") Integer grouperId);

    /*修改组长最近一次登陆时间*/
    @Modifying(clearAutomatically=true)
    @Query("update Grouper set lastTime=:lastTime where grouperId=:grouperId")
    int updateLastTime(@Param("lastTime") String lastTime, @Param("grouperId") Integer grouperId);

    /*根据工号查询组长*/
    Page<Grouper> findAllByUser_JobNum(String jobNum, Pageable pageable);

    /*根据姓名查询组长*/
    Page<Grouper> findAllByUser_Name(String name, Pageable pageable);

    /*查询存在的所有组长*/
    List<Grouper> findAllByUser_Group_GroupIdAndUser_Exist(Integer groupId, Exist exist);

    /*组长表工号是否存在*/
    boolean existsByUser_JobNum(String jobNum);

    /*根据工号和密码查询*/
    List<Grouper> findAllByUser_JobNumAndPassword(String jobNum, String password);

}
