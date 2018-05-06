package com.example.service;

import com.example.domain.entity.AgeRange;
import com.example.utils.UserAgePage;

public interface AgeRangeService extends BaseCrudService<AgeRange,Integer>{

    UserAgePage findAllUserAndAge(Integer page);

    UserAgePage findAllUserAndAge(Integer ageRangeId, Integer page);
}
