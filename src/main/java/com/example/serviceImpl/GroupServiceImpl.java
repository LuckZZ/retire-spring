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
import java.util.ArrayList;
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
//            查找此组是组长身份的用户
            List<User> userGroupers = userDao.findAllByGroupAndRank(group,Rank.grouper);
            List<Grouper> groupers = new ArrayList<>();
            for (User uG: userGroupers) {
                groupers.add(grouperDao.findByUser(uG));
            }
            group.setCount(count);
            group.setGroupers(groupers);
            group.setGroupersName(groupersToName(groupers));
        }
        return groups;
    }

    @Override
    public Group findOneSuper(Integer groupId) {
        Group group = groupDao.findOne(groupId);
        long count = userDao.countByGroup(group);
        //            查找此组是组长身份的用户
        List<User> userGroupers = userDao.findAllByGroupAndRank(group,Rank.grouper);
        List<Grouper> groupers = new ArrayList<>();
        for (User uG: userGroupers) {
            groupers.add(grouperDao.findByUser(uG));
        }
        group.setCount(count);
        group.setGroupers(groupers);
        group.setGroupersName(groupersToName(groupers));
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
        Group newGroup = newGroup();
        for (Integer groupId : groupIds) {
            Group group = groupDao.findOne(groupId);
            userDao.updateGroup(newGroup.getGroupId(), groupId);
        }
        for (Integer groupId : groupIds) {
//            删除分组
            groupDao.delete(groupId);
        }
    }

    @Transactional
    @Override
    public void removeUser(Integer[] userIds) {
        Group newGroup = newGroup();
        Integer newGroupId = newGroup.getGroupId();
        for (Integer userId : userIds) {
//            更改分组
            userDao.updateGroupByUseId(newGroupId, userId);
        }

    }

    /**
     * 查看是否有未分组，如果没有，新建未分组
     * @return
     */
    private Group newGroup(){
        Group newGroup;
        if (!groupDao.existsByGroupName("未分组")){
            newGroup = groupDao.save(new Group("未分组"));
        }else {
            newGroup = groupDao.findByGroupName("未分组");
        }
        return newGroup;
    }


    /**
     * 把组长集合的姓名放到String中
     * @param groupers
     * @return
     */
    private String groupersToName(List<Grouper> groupers){
        if (groupers == null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Grouper grouper : groupers) {
            if (first){
                first=false;
            }else {
                result.append(",");
            }
            result.append(grouper.getUser().getName());
        }
        return result.toString();
    }
}
