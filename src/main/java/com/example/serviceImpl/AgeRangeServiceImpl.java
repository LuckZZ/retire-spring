package com.example.serviceImpl;

import com.example.dao.AgeRangeDao;
import com.example.domain.entity.*;
import com.example.domain.enums.Exist;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Service
public class AgeRangeServiceImpl extends BaseCrudServiceImpl<AgeRange,Integer,AgeRangeDao> implements AgeRangeService{

    @Autowired
    private AgeRangeDao ageRangeDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public AgeRange save(Integer minAge, Integer maxAge) {
        AgeRange ageRange = new AgeRange(minAge, maxAge);
        return  ageRangeDao.save(ageRange);
    }

    @Override
    public UserAgePage findAllUserAndAgeByJobNum(String jobNum, Integer page) {
        UserAgePage userAgeData = new UserAgePage();
        List<User> list = nativeQueryUserAge(SearchType.JobNum, jobNum, page);
//       数据
        userAgeData.setContent(list);
//        当前页数
        userAgeData.setNumber(page);
//      总页数
        userAgeData.setTotalPages(getToalPages(SearchType.JobNum, jobNum));
        return userAgeData;
    }

    @Override
    public UserAgePage findAllUserAndAgeByName(String name, Integer page) {
        UserAgePage userAgeData = new UserAgePage();
        List<User> list = nativeQueryUserAge(SearchType.Name, name, page);
//       数据
        userAgeData.setContent(list);
//        当前页数
        userAgeData.setNumber(page);
//      总页数
        userAgeData.setTotalPages(getToalPages(SearchType.Name, name));
        return userAgeData;
    }

    @Override
    public UserAgePage findAllByAgeRange(Exist exist, Integer ageRangeId, Integer page) {
        UserAgePage userAgeData = new UserAgePage();
        //        当前页数
        userAgeData.setNumber(page);
        Map<String, Integer> map;
        if (ageRangeId != -1){
            AgeRange ageRange = ageRangeDao.findOne(ageRangeId);
            List<User> list = nativeQueryUserAgeByRange(exist, ageRange.getMinAge(), ageRange.getMaxAge(), page);
//        数据
            userAgeData.setContent(list);
            map = getToalPagesByRange(exist, ageRange.getMinAge(), ageRange.getMaxAge());
        }else {
//            选择所有
            List<User> list = nativeQueryUserAgeByRange(exist, -1, -1, page);
//        数据
            userAgeData.setContent(list);
            map = getToalPagesByRange(exist, -1, -1);
        }
        //      总页数
        userAgeData.setTotalPages(map.get("toalPages"));
//          平均年龄
        userAgeData.setAvgAge(map.get("avgAge"));

        return userAgeData;
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
    private List<User> nativeQueryUserAge(SearchType searchType, String value, Integer page){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u.user_id, u.job_num, u.name, u.gender, g.group_name, du.duty_name, de.department_name, u.birth, u.work_time, u.retire_time, u.pass_time, u.exist, ");
        sql.append(" (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age,  ");
        sql.append(" (YEAR(pass_time)-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(pass_time, '%m%d')) AS pass_age ");
        sql.append(" FROM tb_user u ");
        sql.append(" LEFT JOIN tb_group g ON u.group_id=g.group_id ");
        sql.append(" LEFT JOIN tb_duty du ON u.duty_id=du.duty_id ");
        sql.append(" LEFT JOIN tb_department de ON u.department_id=de.department_id ");
        if (searchType == SearchType.JobNum){
            sql.append(" WHERE u.job_num='"+value+"' ");
        }else if(searchType == SearchType.Name){
            sql.append(" WHERE u.name='"+value+"' ");
        }else {
            sql.append(" WHERE u.exist="+Exist.yes.ordinal()+" ");
        }

        sql.append(" LIMIT "+(page*10)+",10 ");
        //创建原生SQL查询QUERY实例
        Query query =  em.createNativeQuery(sql.toString());
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List objecArraytList = query.getResultList();
        em.close();
        return convert(objecArraytList);
    }

    private List<User> nativeQueryUserAgeByRange(Exist exist, Integer minAge, Integer maxAge, Integer page){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u.user_id, u.job_num, u.name, u.gender, g.group_name, du.duty_name, de.department_name, u.birth, u.work_time, u.retire_time, u.pass_time, u.exist,");
        sql.append(" (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age,  ");
        sql.append(" (YEAR(pass_time)-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(pass_time, '%m%d')) AS pass_age ");
        sql.append(" FROM tb_user u ");
        sql.append(" LEFT JOIN tb_group g ON u.group_id=g.group_id ");
        sql.append(" LEFT JOIN tb_duty du ON u.duty_id=du.duty_id ");
        sql.append(" LEFT JOIN tb_department de ON u.department_id=de.department_id ");
        sql.append(" WHERE u.exist="+exist.ordinal()+" ");
        if (minAge != -1 && maxAge!=-1){
            sql.append("  HAVING age BETWEEN "+minAge+" AND "+maxAge+" ");
        }
        sql.append(" LIMIT "+(page*10)+",10 ");
        //创建原生SQL查询QUERY实例
        Query query =  em.createNativeQuery(sql.toString());
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List objecArraytList = query.getResultList();
        em.close();
        return convert(objecArraytList);
    }

    private int getToalPages(SearchType searchType, String value){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) ");
        sql.append(" FROM tb_user u ");
        if (searchType == SearchType.JobNum){
            sql.append(" WHERE u.job_num='"+value+"' ");
        }else if(searchType == SearchType.Name){
            sql.append(" WHERE u.name='"+value+"' ");
        }else {
            sql.append(" WHERE u.exist="+Exist.yes.ordinal()+" ");
        }
        //创建原生SQL查询QUERY实例
        Query query =  em.createNativeQuery(sql.toString());
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List objecArraytList = query.getResultList();
        em.close();
        Object obj =  objecArraytList.get(0);
        int toalPages = Integer.parseInt(obj.toString())/10+1;
        return toalPages;
    }

    /**
     SELECT COUNT(*), AVG(u.age) AS avg_age, AVG(u.pass_age ) AS avg_pass_age FROM
     (
     SELECT
     (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age ,
     (YEAR(pass_time)-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(pass_time, '%m%d')) AS pass_age
     FROM tb_user
     WHERE exist=0
     HAVING age BETWEEN 3 AND 5
     ) u
     */
    private Map<String, Integer> getToalPagesByRange(Exist exist, Integer minAge, Integer maxAge){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*), ROUND(AVG(u.age), 0) AS avg_age, ROUND(AVG(u.pass_age), 0) AS avg_pass_age ");
        sql.append(" FROM ( ");
        sql.append(" SELECT ");
        sql.append(" (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age , ");
        sql.append(" (YEAR(pass_time)-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(pass_time, '%m%d')) AS pass_age ");
        sql.append(" FROM tb_user ");
        sql.append(" WHERE exist="+exist.ordinal()+" ");
        if (minAge != -1 && maxAge!=-1){
            if (exist == Exist.yes){
                sql.append(" HAVING age BETWEEN "+minAge+" AND "+maxAge+" ");
            }else if(exist == Exist.no){
                sql.append(" HAVING pass_age BETWEEN "+minAge+" AND "+maxAge+" ");
            }
        }
        sql.append(" ) u ");

        //创建原生SQL查询QUERY实例
        Query query =  em.createNativeQuery(sql.toString());
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List objecArraytList = query.getResultList();
        em.close();
        Object[] obj =  (Object[]) objecArraytList.get(0);
        int toalPages = Integer.parseInt(obj[0] == null ? "0" : obj[0].toString())/10+1;
        int avgAge = 0;
        if (exist == Exist.yes){
            avgAge = Integer.parseInt(obj[1] == null ? "0" : obj[1].toString());
        }else if(exist == Exist.no){
            avgAge = Integer.parseInt(obj[2] == null ? "0" : obj[2].toString());
        }
        Map<String, Integer> map = new HashMap();
        map.put("toalPages",toalPages);
        map.put("avgAge",avgAge);
        return map;
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
            user.setExist(Exist.values()[Integer.parseInt(obj[11] == null ? "" : obj[11].toString())]);
            if (user.getExist() == Exist.yes){
                user.setAge(obj[12] == null ? "" : obj[12].toString());
            }else if (user.getExist() == Exist.no){
                user.setAge(obj[13] == null ? "" : obj[13].toString());
            }
            lists.add(user);
        }
        return lists;
    }

    /*搜索类型*/
    enum SearchType{
        No, JobNum, Name;
    }
}
