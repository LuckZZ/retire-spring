package com.example.serviceImpl;

import com.example.dao.DutyDao;
import com.example.domain.entity.Duty;
import com.example.service.DutyService;
import org.springframework.stereotype.Service;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Service
public class DutyServiceImpl extends BaseCrudServiceImpl<Duty,Integer,DutyDao> implements DutyService{
}
