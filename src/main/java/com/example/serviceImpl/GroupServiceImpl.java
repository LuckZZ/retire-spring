package com.example.serviceImpl;

import com.example.dao.GroupDao;
import com.example.domain.entity.Group;
import com.example.service.GroupService;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl extends BaseCrudServiceImpl<Group, Integer, GroupDao> implements GroupService{
}
