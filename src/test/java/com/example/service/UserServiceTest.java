package com.example.service;

import com.example.RetireSpringApplication;
import com.example.dao.GrouperDao;
import com.example.dao.UserDao;
import com.example.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private GrouperDao grouperDao;

    @Autowired
    private UserDao userDao;

    /**
     * 测试增加管理员
     */
    @Test
    public void save(){
//        Admin admin = new Admin("111111","zhansan","123456","2018", CanLogin.yes);
//        userService.save(admin);
    }
    @Test
    public void findAll(){
        userService.findAll();
    }

    @Test
    public void findOne(){
        User user = userService.findOne(4);
        System.out.println(user.toString());
    }

    @Test
    public void update(){
        User user = userService.findOne(4);
        user.setName("abcd");
        userService.save(user);
    }

    @Test
    public void updateGroup(){
        userDao.updateGroup(17,20);
    }

}
