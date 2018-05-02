package com.example.service;

import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.domain.result.Response;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService extends BaseCrudService<Admin,Integer>{

    List<Admin> findAllByJobNumAndPassword(String jobNum, String password);

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

    Page<Admin> findAllByJobNum(String jobNum, Integer page);

    Page<Admin> findAllByName(String name, Integer page);

}
