package com.example.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 此接口为基础接口，增、删、改、查方法
 * @param <T>
 * @param <ID>
 */
public interface BaseCrudService<T, ID extends Serializable> extends BaseReadService<T,ID>{

    /**
     * 保存一个实体
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 根据id删除
     * @param id
     */
    void delete(ID id);
}
