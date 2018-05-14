package com.example.service;

import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Verify;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService extends BaseCrudService<Admin,Integer>{

    /*根据adminId修改图片*/
    int updateImg(String imgUrl, Integer adminId);

    /*根据adminId修改邮箱和验证情况*/
    int updateEmail(String email, Verify verify, Integer adminId);

    /*根据adminId修改验证情况*/
    int updateVerify(Verify verify, Integer adminId);

    int updateVerifyCode(String verifyCode, String codeTime, Integer adminId);

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

    /**
     *
     * @param adminId 管理员id
     * @return
     */
    boolean notCanLogin(Integer adminId);

    /*根据工号查询管理员*/
    Page<Admin> findAllByJobNum(String jobNum, Integer page);

    /*根姓名查询管理员*/
    Page<Admin> findAllByName(String name, Integer page);

    /*修改管理员最近一次登陆*/
    int updateLastTime(String lastTime, Integer adminId);

}
