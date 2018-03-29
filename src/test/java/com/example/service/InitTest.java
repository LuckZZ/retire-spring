package com.example.service;

import com.example.RetireSpringApplication;
import com.example.domain.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class InitTest {
    @Autowired
    private GroupService groupService;

    @Autowired
    private NationService nationService;

    @Autowired
    private PoliticsService politicsService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DutyService dutyService;

    @Test
    public void save(){
//        Group one = new Group("组一");
//        Group two = new Group("组二");
//        groupService.save(one);
//        groupService.save(two);

        Nation nation1 = new Nation("汉族");
        Nation nation2 = new Nation("壮族");
        nationService.save(nation1);
        nationService.save(nation2);

        Politics politics1 = new Politics("群众");
        Politics politics2 = new Politics("党员");
        politicsService.save(politics1);
        politicsService.save(politics2);

        Department department1 = new Department("部门1");
        Department department2 = new Department("部门2");
        departmentService.save(department1);
        departmentService.save(department2);

        Duty duty1 = new Duty("讲师");
        Duty duty2 = new Duty("教授");
        dutyService.save(duty1);
        dutyService.save(duty2);
    }
}
