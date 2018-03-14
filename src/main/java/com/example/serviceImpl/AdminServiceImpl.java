package com.example.serviceImpl;

import com.example.dao.AdminDao;
import com.example.domain.entity.Admin;
import com.example.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends BaseCrudServiceImpl<Admin,Integer,AdminDao> implements AdminService{
}
