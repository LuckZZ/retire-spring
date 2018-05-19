package com.example.serviceImpl;

import com.example.dao.NationDao;
import com.example.domain.entity.Nation;
import com.example.service.NationService;
import org.springframework.stereotype.Service;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Service
public class NationServiceImpl extends BaseCrudServiceImpl<Nation,Integer,NationDao> implements NationService{

}
