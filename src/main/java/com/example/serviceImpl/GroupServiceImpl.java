package com.example.serviceImpl;

import com.example.dao.GroupDao;
import com.example.dao.GrouperDao;
import com.example.dao.UserDao;
import com.example.domain.entity.Group;
import com.example.domain.entity.Grouper;
import com.example.domain.entity.User;
import com.example.domain.enums.Rank;
import com.example.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl extends BaseCrudServiceImpl<Group, Integer, GroupDao> implements GroupService{
    @Autowired
    private GroupDao groupDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GrouperDao grouperDao;

    @Override
    public List<Group> findAllCustom() {
        List<Group> groups = groupDao.findAllByOrderByGroupId();
        for (Group group : groups) {
            long count = userDao.countByGroup(group);
            User user = userDao.findByGroupAndRank(group,Rank.grouper);
            Grouper grouper = grouperDao.findByUser(user);
            group.setCount(count);
            group.setGrouper(grouper);
        }
        return groups;
    }

    @Override
    public List<Group> findAll() {
        return groupDao.findAllByOrderByGroupId();
    }

    @Override
    public boolean existsByGroupName(String groupName) {
        return groupDao.existsByGroupName(groupName);
    }
}
