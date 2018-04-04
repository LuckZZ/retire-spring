package com.example.service;

import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.domain.result.Response;

public interface AdminService extends BaseCrudService<Admin,Integer>{

    boolean existsByJobNum(String jobNum);

    int updateNameAndCanLogin(String name, CanLogin canLogin, Integer adminId);

    int updatePassword(String password, Integer adminId);

    boolean canDelete(Integer[] adminIds);

    void delete(Integer[] adminIds);

    /**
     *
     * @param adminId 管理员id
     * @return
     */
    boolean notCanLogin(Integer adminId);

}
