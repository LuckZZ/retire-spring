package com.example.serviceImpl;

import com.example.dao.DemoDao;
import com.example.domain.entity.Demo;
import com.example.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl extends BaseCrudServiceImpl<Demo,Long,DemoDao> implements DemoService{

}
