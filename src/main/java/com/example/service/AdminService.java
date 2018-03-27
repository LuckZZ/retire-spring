package com.example.service;

import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;

public interface AdminService extends BaseCrudService<Admin,Integer>{

    boolean existsByJobNum(String jobNum);

    int updateNameAndCanLogin(String name, CanLogin canLogin, Integer adminId);

    int updatePassword(String password, Integer adminId);
}
