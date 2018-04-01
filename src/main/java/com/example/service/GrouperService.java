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
}
