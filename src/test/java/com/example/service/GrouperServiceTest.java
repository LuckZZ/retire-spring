package com.example.service;

import com.example.RetireSpringApplication;
import com.example.domain.entity.Grouper;
import com.example.domain.entity.User;
import com.example.domain.enums.CanLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GrouperServiceTest {
    @Autowired
    private GrouperService grouperService;

    @Autowired
    private UserService userService;

    /**
     * 测试增加组长
     */
    @Test
    public void testSaveGrouper(){
        User user = userService.findOne(1);
        Grouper grouper = new Grouper(user,"123456", CanLogin.yes);
        grouperService.save(grouper);
    }
}
