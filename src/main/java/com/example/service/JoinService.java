package com.example.service;

import com.example.domain.bean.JoinsDisplay;
import com.example.domain.entity.Join;
import com.example.domain.enums.Attend;

public interface JoinService extends BaseCrudService<Join,Integer>{

    JoinsDisplay joinNo(Integer activityId);

    Join save(Integer userId, Integer activityId, String[] inputDefs, String attend);
}
