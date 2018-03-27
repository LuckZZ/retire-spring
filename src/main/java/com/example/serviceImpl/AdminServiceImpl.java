package com.example.serviceImpl;

import com.example.dao.AdminDao;
import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AdminServiceImpl extends BaseCrudServiceImpl<Admin,Integer,AdminDao> implements AdminService{
    @Autowired
    private AdminDao adminDao;
    public  boolean existsByJobNum(String jobNum){
        return adminDao.existsByJobNum(jobNum);
    }

    @Transactional
    @Override
    public int updateNameAndCanLogin(String name, CanLogin canLogin, Integer adminId) {
        return adminDao.updateNameAndCanLogin(name,canLogin,adminId);
    }

    @Transactional
    @Override
    public int updatePassword(String password, Integer adminId) {
        return adminDao.updatePassword(password,adminId);
    }
}
