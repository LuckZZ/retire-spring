package com.example.serviceImpl;

import com.example.dao.AgeRangeDao;
import com.example.domain.entity.*;
import com.example.domain.enums.Gender;
import com.example.service.AgeRangeService;
import com.example.utils.UserAgePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgeRangeServiceImpl extends BaseCrudServiceImpl<AgeRange,Integer,AgeRangeDao> implements AgeRangeService{

    @Autowired
    private AgeRangeDao ageRangeDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public UserAgePage findAllUserAndAge(Integer page) {
        UserAgePage userAgeData = new UserAgePage();
        List<User> list = nativeQueryUserAge(page);
//       数据
        userAgeData.setContent(list);
//        当前页数
        userAgeData.setNumber(page);
//      总页数
        userAgeData.setTotalPages(getToalPages());
        return userAgeData;
    }

    @Override
    public UserAgePage findAllUserAndAge(Integer ageRangeId, Integer page) {
        UserAgePage userAgeData = new UserAgePage();
        return userAgeData;
    }

    @Override
    public AgeRange save(Integer minAge, Integer maxAge) {
        AgeRange ageRange = new AgeRange(minAge, maxAge);
        return  ageRangeDao.save(ageRange);
    }

    /**
     * SELECT user_id, job_num, NAME, gender, group_id, duty_id, department_id, birth, work_time, retire_time, pass_time,
     (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age
     FROM tb_user HAVING age=3 LIMIT 0,10;

     SELECT u.user_id, u.job_num, u.name, u.gender, g.group_name, du.duty_name, de.department_name, u.birth, u.work_time, u.retire_time, u.pass_time,
     (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age
     FROM tb_user u
     LEFT JOIN tb_group g ON u.group_id=g.group_id
     LEFT JOIN tb_duty du ON u.duty_id=du.duty_id
     LEFT JOIN tb_department de ON u.department_id=de.department_id
     * @return
     */
    private List<User> nativeQueryUserAge(Integer page){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u.user_id, u.job_num, u.name, u.gender, g.group_name, du.duty_name, de.department_name, u.birth, u.work_time, u.retire_time, u.pass_time, ");
        sql.append(" (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age  ");
        sql.append(" FROM tb_user u ");
        sql.append(" LEFT JOIN tb_group g ON u.group_id=g.group_id ");
        sql.append(" LEFT JOIN tb_duty du ON u.duty_id=du.duty_id ");
        sql.append(" LEFT JOIN tb_department de ON u.department_id=de.department_id ");
        sql.append(" LIMIT "+(page*10)+",10 ");

        //创建原生SQL查询QUERY实例
        Query query =  em.createNativeQuery(sql.toString());
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List objecArraytList = query.getResultList();
        em.close();
        return convert(objecArraytList);
    }

    private int getToalPages(){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) ");
        sql.append(" FROM tb_user u ");
        sql.append(" LEFT JOIN tb_group g ON u.group_id=g.group_id ");
        sql.append(" LEFT JOIN tb_duty du ON u.duty_id=du.duty_id ");
        sql.append(" LEFT JOIN tb_department de ON u.department_id=de.department_id ");

        //创建原生SQL查询QUERY实例
        Query query =  em.createNativeQuery(sql.toString());
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List objecArraytList = query.getResultList();
        em.close();
        Object obj =  objecArraytList.get(0);
        int toalPages = Integer.parseInt(obj.toString())/10;
        return toalPages;
    }

    private List<User> convert(List objecArraytList) {
        List<User> lists = new ArrayList<>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            User user = new User();
            Object[] obj = (Object[]) objecArraytList.get(i);
            user.setUserId(Integer.parseInt(obj[0].toString()));
            user.setJobNum(obj[1].toString());
            user.setName(obj[2].toString());
            user.setGender(Gender.values()[Integer.parseInt(obj[3] == null ? "" : obj[3].toString())]);
            user.setGroup(new Group(obj[4] == null ? "" : obj[4].toString()));
            user.setDuty(new Duty(obj[5] == null ? "" : obj[5].toString()));
            user.setDepartment(new Department(obj[6] == null ? "" : obj[6].toString()));
            user.setBirth(obj[7] == null ? "" : obj[7].toString());
            user.setWorkTime(obj[8] == null ? "" : obj[8].toString());
            user.setRetireTime(obj[9] == null ? "" : obj[9].toString());
            user.setPassTime(obj[10] == null ? "" : obj[10].toString());
            user.setAge(obj[11] == null ? "" : obj[11].toString());
            lists.add(user);
        }
        return lists;
    }

}
