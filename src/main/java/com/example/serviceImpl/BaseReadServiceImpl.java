package com.example.serviceImpl;

import com.example.service.BaseReadService;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

public class BaseReadServiceImpl<T, ID extends Serializable, D extends JpaRepository<T,ID>> implements BaseReadService<T,ID> {
    @Resource
    D d;
    @Override
    public T findOne(ID id) {
        return d.findOne(id);
    }

    @Override
    public List<T> findAll() {
        return d.findAll();
    }

    @Override
    public long count() {
        return d.count();
    }

    @Override
    public boolean exists(ID id) {
        return d.exists(id);
    }

}
