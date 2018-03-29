package com.example.service;

import com.example.domain.entity.User;

public interface UserService extends BaseCrudService<User,Integer>{

    boolean existsByJobNum(String jobNum);

    void delete(Integer[] userIds);
}
