package com.example.service;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 此接口为基础接口，增、删、改、查方法
 * @param <T>
 * @param <ID>
 */
public interface BaseCrudService<T, ID extends Serializable>{

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

    /**
     * 根据id查出一条数据
     * @param id
     * @return
     */
    T findOne(ID id);

    /**
     * 查找出所有数据
     * @return
     */
    List<T> findAll();

    /**
     * 查出总数量
     * @return
     */
    long count();

    /**
     * 根据id查看是否存在
     * @param id
     * @return
     */
    boolean exist(ID id);

    /**
     * 分页，查出所有的数据
     * @param page 页码
     * @return
     */
    Page<T> findAllNoCriteria(Integer page);
}
