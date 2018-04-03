package com.example.service;

import com.example.domain.entity.Group;

import java.util.List;

public interface GroupService extends BaseCrudService<Group,Integer>{
    /**
     * 实现查找Group表，并未Group对象的grouper和count赋值
     * @return
     */
    List<Group> findAllCustom();

    /**
     * 按照groupId查找group，并把gouper、count和List<User>赋值
     * @param groupId
     * @return
     */
    Group findOneSuper(Integer groupId);

    @Override
    List<Group> findAll();

    boolean existsByGroupName(String groupName);

    void delete(Integer[] groupIds);

    void removeUser(Integer[] userIds);
}
