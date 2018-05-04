package com.example.serviceImpl;

import com.example.comm.Constant;
import com.example.dao.GroupDao;
import com.example.dao.GrouperDao;
import com.example.dao.UserDao;
import com.example.domain.entity.Group;
import com.example.domain.entity.Grouper;
import com.example.domain.enums.Exist;
import com.example.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Group> findAllNoCriteria(Integer page) {
        Page<Group> groups = super.findAllNoCriteria(page);
        for (Group group : groups) {
            long count = userDao.countByGroup_GroupIdAndExist(group.getGroupId(), Exist.yes);
//            查找此组组长
            List<Grouper> groupers = grouperDao.findAllByUser_Group_GroupIdAndUser_Exist(group.getGroupId(), Exist.yes);
            group.setCount(count);
            group.setGroupers(groupers);
            group.setGroupersName(groupersToName(groupers));
        }
        return groups;
    }

    @Override
    public boolean existsByGroupName(String groupName) {
        return groupDao.existsByGroupName(groupName);
    }

    @Transactional
    @Override
    public void delete(Integer[] groupIds) {
//        查看是否有未分组，如果没有，新建未分组
        for (Integer groupId : groupIds) {
            long count = userDao.countByGroup_GroupIdAndExist(groupId, Exist.yes);
            if (count != 0){
                Group newGroup = newGroup();
                userDao.updateGroup(newGroup.getGroupId(), groupId);
            }
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

    @Transactional
    @Override
    public int updateGroupName(String groupName, Integer groupId) {
        return groupDao.updateGroupName(groupName, groupId);
    }

    @Override
    public Page<Group> findAllByGroupName(String groupName, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        Page<Group> groups = groupDao.findAllByGroupName(groupName, pageable);
        for (Group group : groups) {
            long count = userDao.countByGroup_GroupIdAndExist(group.getGroupId(), Exist.yes);
//            查找此组组长
            List<Grouper> groupers = grouperDao.findAllByUser_Group_GroupIdAndUser_Exist(group.getGroupId(), Exist.yes);
            group.setCount(count);
            group.setGroupers(groupers);
            group.setGroupersName(groupersToName(groupers));
        }
        return groups;
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
