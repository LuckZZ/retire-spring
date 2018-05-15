package com.example.service;

import com.example.RetireSpringApplication;
import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    /**
     * 测试增加管理员
     */
    @Test
    public void save(){
    /*    Admin admin = new Admin("111111","zhansan","123456","2018", CanLogin.yes);
        adminService.save(admin);*/
    }
    @Test
    public void findAll(){
        adminService.findAll();
    }

    @Test
    public void testFindByLoginType(){
        //adminService.findByLoginType(Role.Zuzhang);
    }

    @Test
    public void testFindAllByJobNum(){
        List<Admin> admins = adminService.findAllByJobNum("789789");
        System.out.println("size:"+admins.size());
    }
}
