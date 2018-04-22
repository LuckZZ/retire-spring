package com.example.service;

import com.example.domain.entity.Group;

public interface GroupService extends BaseCrudService<Group,Integer>{

    boolean existsByGroupName(String groupName);

    void delete(Integer[] groupIds);

    void removeUser(Integer[] userIds);

    int updateGroupName(String groupName, Integer groupId);

}
