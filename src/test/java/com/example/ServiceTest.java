package com.example;

import com.example.dao.JoinDao;
import com.example.dao.UserDao;
import com.example.service.JoinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Create by : Zhangxuemeng on 2018/5/27
 * Csdn blog：https://blog.csdn.net/Luck_ZZ
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ServiceTest {

    @Autowired
    private JoinService joinService;

    @Test
    public void test(){
//        joinService.deleteAllByActivity_ActivityIdAndUser_UserId(3,5);
    }


}