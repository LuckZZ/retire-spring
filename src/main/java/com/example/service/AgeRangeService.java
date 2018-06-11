package com.example.service;

import com.example.domain.entity.AgeRange;
import com.example.domain.entity.User;
import com.example.domain.enums.Exist;
import com.example.utils.UserAgePage;

import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface AgeRangeService extends BaseCrudService<AgeRange,Integer>{

    /*保存*/
    AgeRange save(Integer minAge, Integer maxAge);

    /*根据jobNum查询*/
    UserAgePage findAllUserAndAgeByJobNum(String jobNum, Integer page);

    List<User> findAllUserAndAgeByJobNum(String jobNum);

    /*根据name查询*/
    UserAgePage findAllUserAndAgeByName(String name, Integer page);

    List<User> findAllUserAndAgeByName(String name);

    /*查找年龄范围内用户*/
    UserAgePage findAllByAgeRange(Exist exist, Integer ageRangeId, Integer page);

    List<User> findAllByAgeRange(Exist exist, Integer ageRangeId);

    List<User> findAllByUserIds(Integer[] userIds);

}
