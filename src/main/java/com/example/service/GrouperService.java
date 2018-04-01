package com.example.service;

import com.example.domain.entity.Grouper;

public interface GrouperService extends BaseCrudService<Grouper,Integer>{

    /**
     * 是否为组长，取反
     * @param userId
     * @return
     */
    boolean notGrouper(Integer userId);
}
