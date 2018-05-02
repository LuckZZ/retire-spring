package com.example.service;

import com.example.domain.entity.Grouper;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GrouperService extends BaseCrudService<Grouper,Integer>{

    /**
     * 是否为组长，取反
     * @param userId 用户id
     * @return
     */
    boolean notGrouper(Integer userId);

    /**
     *
     * @param grouperId 组长id
     * @return
     */
    boolean notCanLogin(Integer grouperId);

    int updatePwd(String password, Integer grouperId);

    /**
     * 从组长表中删除组长，并设置用户表用户的rank值
     * @param grouperIds
     */
    void remove(Integer[] grouperIds);

    Page<Grouper> findAllByUser_JobNum(String jobNum, Integer page);

    Page<Grouper> findAllByUser_Name(String name, Integer page);

    /*组长表工号是否存在*/
    boolean existsByJobNum(String jobNum);

    /*根据工号和密码查询*/
    List<Grouper> findAllByJobNumAndPassword(String jobNum, String password);

}
