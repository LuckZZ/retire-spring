package com.example.serviceImpl;
import com.example.dao.UserDao;
import com.example.domain.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseCrudServiceImpl<User, Integer, UserDao> implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public boolean existsByJobNum(String jobNum) {
        return userDao.existsByJobNum(jobNum);
    }
}
