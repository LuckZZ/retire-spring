package com.example.serviceImpl;

import com.example.dao.PoliticsDao;
import com.example.domain.entity.Politics;
import com.example.service.PoliticsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Service
public class PoliticsServiceImpl extends BaseCrudServiceImpl<Politics,Integer,PoliticsDao> implements PoliticsService{

}
