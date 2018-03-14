package com.example.serviceImpl;

import com.example.dao.AdminDao;
import com.example.dao.GrouperDao;
import com.example.domain.entity.Admin;
import com.example.domain.entity.Group;
import com.example.domain.entity.Grouper;
import com.example.service.AdminService;
import com.example.service.GroupService;
import com.example.service.GrouperService;
import org.springframework.stereotype.Service;

@Service
public class GrouperServiceImpl extends BaseCrudServiceImpl<Grouper,Integer,GrouperDao> implements GrouperService{
}
