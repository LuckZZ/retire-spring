package com.example.serviceImpl;

import com.example.dao.NationDao;
import com.example.domain.entity.Nation;
import com.example.service.NationService;
import org.springframework.stereotype.Service;

@Service
public class NationServiceImpl extends BaseCrudServiceImpl<Nation,Integer,NationDao> implements NationService{

}
