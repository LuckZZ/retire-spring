package com.example.serviceImpl;

import com.example.dao.DepartmentDao;
import com.example.domain.entity.Department;
import com.example.service.DepartmentService;
import org.springframework.stereotype.Service;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Service
public class DepartmentServiceImpl extends BaseCrudServiceImpl<Department,Integer,DepartmentDao> implements DepartmentService{
}
