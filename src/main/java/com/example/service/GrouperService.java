package com.example.service;

import com.example.domain.entity.Grouper;
import com.example.domain.enums.Verify;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface GrouperService extends BaseCrudService<Grouper,Integer>{

    /*修改组长最近一次登陆时间*/
    int updateLastTime(String lastTime, Integer grouperId);

    /**
     * 是否为组长，取反
     * @param userId 用户id
     * @return
     */
    boolean notGrouper(Integer userId);

    /*根据grouperId切换登陆权限*/
    boolean notCanLogin(Integer grouperId);

    /*修改组长密码*/
    int updatePassword(String password, Integer grouperId);

     /*从组长表中删除组长，并设置用户表用户的rank值*/
    void remove(Integer[] grouperIds);

    /*根据工号，查询组长*/
    Page<Grouper> findAllByJobNum(String jobNum, Integer page);

    List<Grouper> findAllByJobNum(String jobNum);

    /*根据姓名，查询组长*/
    Page<Grouper> findAllByName(String name, Integer page);

    /*组长表工号是否存在*/
    boolean existsByJobNum(String jobNum);

    /*根据工号和密码查询*/
    List<Grouper> findAllByJobNumAndPassword(String jobNum, String password);

    /*根据grouperId修改邮箱和验证情况*/
    int updateEmail(String email, Verify verify, Integer grouperId);

    /*根据grouperId修改验证码和发送邮箱时间情况*/
    int updateVerifyCode(String verifyCode, String verifyTime, Integer grouperId);

    /*根据grouperId修改验证情况*/
    int updateVerify(Verify verify, Integer grouperId);

}
