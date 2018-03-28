package com.example.serviceImpl;

import com.example.dao.DepartmentDao;
import com.example.domain.entity.Department;
import com.example.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends BaseCrudServiceImpl<Department,Integer,DepartmentDao> implements DepartmentService{
}
