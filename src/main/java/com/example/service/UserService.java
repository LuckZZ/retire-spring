package com.example.service;

import com.example.domain.entity.User;

import java.util.List;

public interface UserService extends BaseCrudService<User,Integer>{

    boolean existsByJobNum(String jobNum);

    void delete(Integer[] userIds);

    boolean updateExceptId(User user);

    List<User> findAllByJobNum(String jobNum);

    List<User> findAllByName(String name);

    int updateGroupByUseId(Integer groupId, Integer userId);
}
