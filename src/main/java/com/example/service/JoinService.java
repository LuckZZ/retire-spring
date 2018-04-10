package com.example.service;

import com.example.domain.bean.JoinsDisplay;
import com.example.domain.entity.Join;

public interface JoinService extends BaseCrudService<Join,Integer>{

    JoinsDisplay joinNo(Integer activityId);

}
