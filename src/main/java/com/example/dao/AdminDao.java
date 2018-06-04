package com.example.dao;

import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Verify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {

    /*根据adminId修改图片*/
    @Modifying(clearAutomatically=true)
    @Query("update Admin set imgUrl=:imgUrl where adminId=:adminId")
    int updateImg(@Param("imgUrl") String imgUrl, @Param("adminId") Integer adminId);

    /*根据工号查看管理员是否存在*/
    boolean existsByJobNum(String jobNum);

    /*根据工号和密码查询管理员列表*/
    List<Admin> findAllByJobNumAndPassword(String jobNum, String password);

    /*修改管理员姓名和登录权限*/
    @Modifying(clearAutomatically=true)
    @Query("update Admin set name=:name,canLogin=:canLogin  where adminId=:adminId")
    int updateNameAndCanLogin(@Param("name") String name, @Param("canLogin") CanLogin canLogin, @Param("adminId") Integer adminId);

    /*修改管理员密码*/
    @Modifying(clearAutomatically=true)
    @Query("update Admin set password=:password where adminId=:adminId")
    int updatePassword(@Param("password") String password, @Param("adminId") Integer adminId);

    /*修改管理员登录权限*/
    @Modifying(clearAutomatically=true)
    @Query("update Admin set canLogin=:canLogin where adminId=:adminId")
    int updateCanLogin(@Param("canLogin") CanLogin canLogin, @Param("adminId") Integer adminId);

    /*修改管理员最近一次登陆时间*/
    @Modifying(clearAutomatically=true)
    @Query("update Admin set lastTime=:lastTime where adminId=:adminId")
    int updateLastTime(@Param("lastTime") String lastTime, @Param("adminId") Integer adminId);

    /*根据工号查询管理员*/
    Page<Admin> findAllByJobNum(String jobNum, Pageable pageable);

    List<Admin> findAllByJobNum(String jobNum);

    /*根姓名查询管理员*/
    Page<Admin> findAllByNameContaining(String name, Pageable pageable);

    /*根据adminId修改邮箱和验证情况*/
    @Modifying(clearAutomatically=true)
    @Query("update Admin set email=:email,verify=:verify where adminId=:adminId")
    int updateEmail(@Param("email") String email, @Param("verify") Verify verify, @Param("adminId") Integer adminId);

    /*根据adminId修改验证码和发送邮箱时间情况*/
    @Modifying(clearAutomatically=true)
    @Query("update Admin set verifyCode=:verifyCode,verifyTime=:verifyTime where adminId=:adminId")
    int updateVerifyCode(@Param("verifyCode") String verifyCode, @Param("verifyTime") String verifyTime, @Param("adminId") Integer adminId);

    /*根据adminId修改邮箱验证情况*/
    @Modifying(clearAutomatically=true)
    @Query("update Admin set verify=:verify where adminId=:adminId")
    int updateVerify(@Param("verify") Verify verify, @Param("adminId") Integer adminId);

}
