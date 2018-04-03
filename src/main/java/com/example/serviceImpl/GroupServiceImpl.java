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

import javax.transaction.Transactional;
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
    public Group findOneSuper(Integer groupId) {
        Group group = groupDao.findOne(groupId);
        long count = userDao.countByGroup(group);
        User userGrouper = userDao.findByGroupAndRank(group,Rank.grouper);
        Grouper grouper = grouperDao.findByUser(userGrouper);
        group.setCount(count);
        group.setGrouper(grouper);

        List<User> users = userDao.findAllByGroup(group);
        group.setUsers(users);

        return group;
    }

    @Override
    public List<Group> findAll() {
        return groupDao.findAllByOrderByGroupId();
    }

    @Override
    public boolean existsByGroupName(String groupName) {
        return groupDao.existsByGroupName(groupName);
    }

    @Transactional
    @Override
    public void delete(Integer[] groupIds) {
//        查看是否有未分组，如果没有，新建未分组
        Group newGroup;
        if (!groupDao.existsByGroupName("未分组")){
            newGroup = groupDao.save(new Group("未分组"));
        }else {
            newGroup = groupDao.findByGroupName("未分组");
        }

        for (Integer groupId : groupIds) {
            Group group = groupDao.findOne(groupId);
//            把此组的组长设为组员，没有组长，不进行设置
            User userGrouper = userDao.findByGroupAndRank(group,Rank.grouper);
            if (userGrouper != null){
                userDao.updateRank(Rank.user,userGrouper.getUserId());
            }
            userDao.updateGroup(newGroup.getGroupId(), groupId);
        }

        for (Integer groupId : groupIds) {
//            删除分组
            groupDao.delete(groupId);
        }

    }
}
