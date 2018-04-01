package com.example.serviceImpl;

import com.example.dao.GrouperDao;
import com.example.dao.UserDao;
import com.example.domain.entity.Grouper;
import com.example.domain.entity.User;
import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Rank;
import com.example.service.GrouperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GrouperServiceImpl extends BaseCrudServiceImpl<Grouper,Integer,GrouperDao> implements GrouperService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private GrouperDao grouperDao;

    /**
     * 用于设置用户是否为组长
     * 如设置为组长：保存到grouper表中；更改user的rank字段
     * 如设置为组员：从grouper表中删除；更改user的rank字段
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public boolean notGrouper(Integer userId) {
        User user = userDao.findOne(userId);
        Rank oldRank = user.getRank();
        Rank newRank = Rank.user;
        if (oldRank == Rank.grouper){
//            以前是组长，现在设置为组员
            newRank = Rank.user;
//            删除组长
            grouperDao.deleteByUser(user);
        }else if (oldRank == Rank.user){
//            以前是组员，现在设置为组长
            newRank = Rank.grouper;
            grouperDao.save(new Grouper(user,"123456", CanLogin.no));
        }
//            更改用户rank字段
        userDao.updateRank(newRank,userId);
        return true;
    }
}
