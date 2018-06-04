package com.example.service;

import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Verify;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface AdminService extends BaseCrudService<Admin,Integer>{

    /*根据adminId修改图片*/
    int updateImg(String imgUrl, Integer adminId);

    /*根据工号和密码查询所有管理员*/
    List<Admin> findAllByJobNumAndPassword(String jobNum, String password);

    /*根据工号查看是否存在此管理员*/
    boolean existsByJobNum(String jobNum);

    /*修改管理员姓名和登录权限*/
    int updateNameAndCanLogin(String name, CanLogin canLogin, Integer adminId);

    /*修改管理员密码*/
    int updatePassword(String password, Integer adminId);

    boolean canDelete(Integer[] adminIds);

    /*批量删除管理员*/
    void delete(Integer[] adminIds);

    /*根据adminId切换登陆权限*/
    boolean notCanLogin(Integer adminId);

    /*根据工号查询管理员*/
    Page<Admin> findAllByJobNum(String jobNum, Integer page);

    List<Admin> findAllByJobNum(String jobNum);

    /*根姓名查询管理员*/
    Page<Admin> findAllByNameContaining(String name, Integer page);

    /*修改管理员最近一次登陆*/
    int updateLastTime(String lastTime, Integer adminId);

    /*根据adminId修改邮箱和验证情况*/
    int updateEmail(String email, Verify verify, Integer adminId);

    /*根据adminId修改验证码和发送邮箱时间情况*/
    int updateVerifyCode(String verifyCode, String verifyTime, Integer adminId);

    /*根据adminId修改验证情况*/
    int updateVerify(Verify verify, Integer adminId);

}
