package com.example.service;

import com.example.RetireSpringApplication;
import com.example.dao.GrouperDao;
import com.example.dao.JoinDao;
import com.example.dao.UserDao;
import com.example.domain.entity.Join;
import com.example.domain.entity.JoinDef;
import com.example.domain.entity.User;
import com.example.serviceImpl.JoinServiceImpl;
import com.example.utils.Criteria;
import com.example.utils.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JoinServiceTest {
    @Autowired
    private JoinDao joinDao;

    @Test
    public void contextLoads() {
        Criteria<Join> criteria = new Criteria<>();
        criteria.add(Restrictions.hasMembers("joinDefs.input", "时间一","地点一"));
        List<Join> userList = joinDao.findAll(criteria);

        System.out.println("开始");
        for (Join join : userList) {
            System.out.println("jobNum:"+join.getUser().getJobNum()+"   name:"+join.getUser().getName()+"   group:"+join.getUser().getGroup().getGroupName());
            for (JoinDef jd : join.getJoinDefs()) {
                System.out.println("label:"+jd.getActivityDef().getLabel()+"     input:"+jd.getInput());
            }
        }
        System.out.println("结束");
    }

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
