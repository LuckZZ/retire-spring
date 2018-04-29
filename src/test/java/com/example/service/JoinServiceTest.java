package com.example.service;

import com.example.RetireSpringApplication;
import com.example.dao.JoinDao;
import com.example.domain.entity.Join;
import com.example.domain.entity.JoinDef;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JoinServiceTest {
    @Autowired
    private JoinDao joinDao;

    @Test
    public void contextLoad2() {

        Specification<Join> specification = new Specification<Join>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<Join> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<javax.persistence.criteria.Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.join("activity").get("activityId").as(Integer.class), 2));
                ListJoin setJoin = root.joinList("joinDefs");
                Path expression = setJoin.get("input");
//                list.add(cb.equal(expression, "时间一"));
//                list.add(cb.equal(expression, "地点一"));
//                list.add(cb.isMember("时间一","地点一", expression));
                javax.persistence.criteria.Predicate[] p = new javax.persistence.criteria.Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };

        List<Join> userList = joinDao.findAll(specification);

        System.out.println("开始");
        for (Join join : userList) {
            System.out.println("jobNum:"+join.getUser().getJobNum()+"   name:"+join.getUser().getName()+"   group:"+join.getUser().getGroup().getGroupName());
        }
        System.out.println("结束");
    }

}
