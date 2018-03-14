package com.example.service;

import com.example.RetireSpringApplication;
import com.example.domain.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;
    @Test
    public void testSaveAdmin(){
        //Admin admin = new Admin("123456","zhangsan","5555", LoginType.Admin,"2018");
        //adminService.save(admin);
    }
    @Test
    public void testSaveZuzhang(){
       // Admin admin = new Admin("123456","zhangsan","5555", LoginType.Zuzhang,"2018");
        //adminService.save(admin);
    }
    @Test
    public void testFindAll(){
        adminService.findAll();
    }

    @Test
    public void testFindByLoginType(){
        //adminService.findByLoginType(LoginType.Zuzhang);
    }
}
