package com.example.service;

import com.example.domain.entity.Group;

import java.util.List;

public interface GroupService extends BaseCrudService<Group,Integer>{
    /**
     * 实现查找Group表，并未Group对象的grouper和count赋值
     * @return
     */
    List<Group> findAllCustom();
}
