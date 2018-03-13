package com.example.serviceImpl;

import com.example.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 此接口为基础接口，增、删、改、查方法
 * 保存、修改需添加事务注解
 * @param <T>
 * @param <ID>
 * @param <D>
 */
public class BaseCrudServiceImpl<T, ID extends Serializable, D extends JpaRepository<T,ID>> implements BaseCrudService<T,ID> {
    @Autowired
    D d;

    /**
     * 保存增加事务注解
     * @param entity
     * @return
     */
    @Transactional
    @Override
    public T save(T entity) {
        return d.save(entity);
    }

    /**
     * 删除增加事务注解
     * @param id
     */
    @Transactional
    @Override
    public void delete(ID id) {
        d.delete(id);
    }

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
