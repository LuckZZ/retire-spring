package com.example.service;

import com.example.domain.entity.Group;
import org.springframework.data.domain.Page;

public interface GroupService extends BaseCrudService<Group,Integer>{

    boolean existsByGroupName(String groupName);

    void delete(Integer[] groupIds);

    void removeUser(Integer[] userIds);

    int updateGroupName(String groupName, Integer groupId);

    Page<Group> findAllByGroupName(String groupName, Integer page);

}
