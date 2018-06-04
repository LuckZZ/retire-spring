package com.example.service;

import com.example.domain.entity.Group;
import org.springframework.data.domain.Page;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface GroupService extends BaseCrudService<Group,Integer>{

    boolean existsByGroupName(String groupName);

    void delete(Integer[] groupIds);

    void removeUser(Integer[] userIds);

    int updateGroupName(String groupName, Integer groupId);

    Page<Group> findAllByGroupNameContaining(String groupName, Integer page);

}
