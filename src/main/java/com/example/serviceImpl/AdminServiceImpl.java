package com.example.serviceImpl;

import com.example.dao.AdminDao;
import com.example.domain.entity.Admin;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends BaseCrudServiceImpl<Admin,Integer,AdminDao> implements AdminService{
    @Autowired
    private AdminDao adminDao;
    public  boolean existsByJobNum(String jobNum){
        return adminDao.existsByJobNum(jobNum);
    }
}
