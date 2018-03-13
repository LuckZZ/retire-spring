package com.example.service;

import java.io.Serializable;
import java.util.List;

/**
 * 此接口为基础接口，有读方法
 * @param <T>
 * @param <ID>
 */
public interface BaseReadService<T, ID extends Serializable> {
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
    boolean exists(ID id);
}
