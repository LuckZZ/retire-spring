package com.example.service;

import com.example.domain.entity.Grouper;

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
}
