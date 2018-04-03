package com.example.service;

import com.example.RetireSpringApplication;
import com.example.domain.entity.Group;
import com.example.domain.entity.Grouper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GroupServiceTest {
    @Autowired
    private GroupService groupService;

    /**
     * 测试增加组长
     */
    @Test
    public void testSaveGrouper(){
        Group group = groupService.findOne(1);
/*        Integer count = group.getCount();
        System.out.println(count);*/
    }

    @Test
    public void  findAllCustom(){
        List<Group> groups = groupService.findAllCustom();
        for (Group group : groups) {
            System.out.println(group.getCount());
            if (group.getGrouper() != null)
            {
                System.out.println(group.getGrouper().getUser().getName());
            }else {
                System.out.println("null");
            }

        }
    }
}
