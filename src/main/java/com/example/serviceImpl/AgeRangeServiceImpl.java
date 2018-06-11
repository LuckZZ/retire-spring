package com.example.serviceImpl;

import com.example.dao.AgeRangeDao;
import com.example.domain.entity.*;
import com.example.domain.enums.Exist;
import com.example.domain.enums.Gender;
import com.example.domain.enums.Rank;
import com.example.service.AgeRangeService;
import com.example.utils.UserAgePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.*;

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

        Map<ToalPages, Integer> map = getToalPages(SearchType.JobNum, jobNum);

        //      总页数
        userAgeData.setTotalPages(map.get(ToalPages.toalPages));

        userAgeData.setTotalElements(map.get(ToalPages.totalElements));

        return userAgeData;
    }

    @Override
    public List<User> findAllUserAndAgeByJobNum(String jobNum) {
        List<User> list = nativeQueryUserAge(SearchType.JobNum, jobNum, -1);
        return list;
    }

    @Override
    public UserAgePage findAllUserAndAgeByName(String name, Integer page) {
        UserAgePage userAgeData = new UserAgePage();
        List<User> list = nativeQueryUserAge(SearchType.Name, name, page);
//       数据
        userAgeData.setContent(list);
//        当前页数
        userAgeData.setNumber(page);

        Map<ToalPages, Integer> map = getToalPages(SearchType.Name, name);
//      总页数
        userAgeData.setTotalPages(map.get(ToalPages.toalPages));

        userAgeData.setTotalElements(map.get(ToalPages.totalElements));

        return userAgeData;
    }

    @Override
    public List<User> findAllUserAndAgeByName(String name) {
        List<User> list = nativeQueryUserAge(SearchType.Name, name, -1);
        return list;
    }

    @Override
    public UserAgePage findAllByAgeRange(Exist exist, Integer ageRangeId, Integer page) {
        UserAgePage userAgeData = new UserAgePage();
        //        当前页数
        userAgeData.setNumber(page);
        Map<ToalPages, Integer> map;
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
        userAgeData.setTotalPages(map.get(ToalPages.toalPages));
        //      总条数
        userAgeData.setTotalElements(map.get(ToalPages.totalElements));
//          平均年龄
        userAgeData.setAvgAge(map.get(ToalPages.avgAge));

        return userAgeData;
    }

    @Override
    public List<User> findAllByAgeRange(Exist exist, Integer ageRangeId) {
        List<User> list = null;
        if (ageRangeId != -1){
            AgeRange ageRange = ageRangeDao.findOne(ageRangeId);
            list = nativeQueryUserAgeByRange(exist, ageRange.getMinAge(), ageRange.getMaxAge(), -1);
        }else {
//            选择所有
            list = nativeQueryUserAgeByRange(exist, -1, -1, -1);
        }
        return list;
    }

    @Override
    public List<User> findAllByUserIds(Integer[] userIds) {
        if (userIds.length > 0){
            List<User> list = nativeQueryUserAge(userIds, -1);
            return list;
        }
        return null;
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
     page不等于-1才进行分页限制
     * @return
     */
    private List<User> nativeQueryUserAge(SearchType searchType, String value, Integer page){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u.user_id, u.job_num, u.name, u.gender, g.group_name, du.duty_name, de.department_name, u.birth, u.work_time, u.retire_time, u.pass_time, u.exist, ");
        sql.append(" u.rank, na.nation_name, u.tel1, u.tel2, u.tel3, u.mate, u.address, po.politics_name, other,");
        sql.append(" (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age,  ");
        sql.append(" (YEAR(pass_time)-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(pass_time, '%m%d')) AS pass_age ");
        sql.append(" FROM tb_user u ");
        sql.append(" LEFT JOIN tb_group g ON u.group_id=g.group_id ");
        sql.append(" LEFT JOIN tb_duty du ON u.duty_id=du.duty_id ");
        sql.append(" LEFT JOIN tb_department de ON u.department_id=de.department_id ");
        sql.append(" LEFT JOIN tb_nation na ON u.nation_id=na.nation_id ");
        sql.append(" LEFT JOIN tb_politics po ON u.politics_id=po.politics_id ");
        if (searchType == SearchType.JobNum){
            sql.append(" WHERE u.job_num='"+value+"' ");
        }else if(searchType == SearchType.Name){
            sql.append(" WHERE u.name like '%"+value+"%' ");
        }else {
            sql.append(" WHERE u.exist="+Exist.yes.ordinal()+" ");
        }

        if (page != -1){
            sql.append(" LIMIT "+(page*10)+",10 ");
        }

        //创建原生SQL查询QUERY实例
        Query query =  em.createNativeQuery(sql.toString());
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List objecArraytList = query.getResultList();
        em.close();
        return convert(objecArraytList);
    }

    private List<User> nativeQueryUserAge(Integer[] userIds, Integer page){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u.user_id, u.job_num, u.name, u.gender, g.group_name, du.duty_name, de.department_name, u.birth, u.work_time, u.retire_time, u.pass_time, u.exist, ");
        sql.append(" u.rank, na.nation_name, u.tel1, u.tel2, u.tel3, u.mate, u.address, po.politics_name, other,");
        sql.append(" (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age,  ");
        sql.append(" (YEAR(pass_time)-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(pass_time, '%m%d')) AS pass_age ");
        sql.append(" FROM tb_user u ");
        sql.append(" LEFT JOIN tb_group g ON u.group_id=g.group_id ");
        sql.append(" LEFT JOIN tb_duty du ON u.duty_id=du.duty_id ");
        sql.append(" LEFT JOIN tb_department de ON u.department_id=de.department_id ");
        sql.append(" LEFT JOIN tb_nation na ON u.nation_id=na.nation_id ");
        sql.append(" LEFT JOIN tb_politics po ON u.politics_id=po.politics_id ");

        sql.append(" WHERE u.user_id in ( ");

        for (int i = 0; i < userIds.length; i++) {
            if (i == userIds.length-1){
                sql.append(userIds[i]);
            }else{
                sql.append(userIds[i]+",");
            }
        }
        sql.append(" ) ");

        if (page != -1){
            sql.append(" LIMIT "+(page*10)+",10 ");
        }

        //创建原生SQL查询QUERY实例
        Query query =  em.createNativeQuery(sql.toString());
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List objecArraytList = query.getResultList();
        em.close();
        return convert(objecArraytList);
    }

    /**
     * page不等于-1，才对分页限制
     * @param exist
     * @param minAge
     * @param maxAge
     * @param page
     * @return
     */
    private List<User> nativeQueryUserAgeByRange(Exist exist, Integer minAge, Integer maxAge, Integer page){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u.user_id, u.job_num, u.name, u.gender, g.group_name, du.duty_name, de.department_name, u.birth, u.work_time, u.retire_time, u.pass_time, u.exist,");
        sql.append(" u.rank, na.nation_name, u.tel1, u.tel2, u.tel3, u.mate, u.address, po.politics_name, other,");
        sql.append(" (YEAR(NOW())-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(NOW(), '%m%d')) AS age,  ");
        sql.append(" (YEAR(pass_time)-YEAR(birth)-1) + ( DATE_FORMAT(birth, '%m%d') <= DATE_FORMAT(pass_time, '%m%d')) AS pass_age ");
        sql.append(" FROM tb_user u ");
        sql.append(" LEFT JOIN tb_group g ON u.group_id=g.group_id ");
        sql.append(" LEFT JOIN tb_duty du ON u.duty_id=du.duty_id ");
        sql.append(" LEFT JOIN tb_department de ON u.department_id=de.department_id ");
        sql.append(" LEFT JOIN tb_nation na ON u.nation_id=na.nation_id ");
        sql.append(" LEFT JOIN tb_politics po ON u.politics_id=po.politics_id ");
        sql.append(" WHERE u.exist="+exist.ordinal()+" ");
        if (minAge != -1 && maxAge!=-1){
            if (exist == Exist.yes){
                sql.append(" HAVING age BETWEEN "+minAge+" AND "+maxAge+" ");
            }else if(exist == Exist.no){
                sql.append(" HAVING pass_age BETWEEN "+minAge+" AND "+maxAge+" ");
            }
        }

        if (page != -1){
            sql.append(" LIMIT "+(page*10)+",10 ");
        }

        //创建原生SQL查询QUERY实例
        Query query =  em.createNativeQuery(sql.toString());
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List objecArraytList = query.getResultList();
        em.close();
        return convert(objecArraytList);
    }

    private Map<ToalPages, Integer> getToalPages(SearchType searchType, String value){
        EntityManager em = emf.createEntityManager();
//        定义sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) ");
        sql.append(" FROM tb_user u ");
        if (searchType == SearchType.JobNum){
            sql.append(" WHERE u.job_num='"+value+"' ");
        }else if(searchType == SearchType.Name){
            sql.append(" WHERE u.name like '%"+value+"%' ");
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

        int toalPages = 0;
        if((obj != null) && Integer.parseInt(obj.toString())%10 == 0){
            toalPages = Integer.parseInt(obj.toString())/10;
        }else if ((obj != null) && Integer.parseInt(obj.toString())%10 != 0){
            toalPages = Integer.parseInt(obj.toString())/10+1;
        }

        int totalElements = Integer.parseInt(obj.toString());
        Map<ToalPages, Integer> map = new HashMap();
        map.put(ToalPages.toalPages,toalPages);
        map.put(ToalPages.totalElements,totalElements);
        return map;
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
    private Map<ToalPages, Integer> getToalPagesByRange(Exist exist, Integer minAge, Integer maxAge){
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
        int toalPages = 0;
        if((obj[0] != null) && Integer.parseInt(obj[0].toString())%10 == 0){
            toalPages = Integer.parseInt(obj[0].toString())/10;
        }else if ((obj[0] != null) && Integer.parseInt(obj[0].toString())%10 != 0){
            toalPages = Integer.parseInt(obj[0].toString())/10+1;
        }
        int totalElements = Integer.parseInt(obj[0] == null ? "0" : obj[0].toString());
        int avgAge = 0;
        if (exist == Exist.yes){
            avgAge = Integer.parseInt(obj[1] == null ? "0" : obj[1].toString());
        }else if(exist == Exist.no){
            avgAge = Integer.parseInt(obj[2] == null ? "0" : obj[2].toString());
        }
        Map<ToalPages, Integer> map = new HashMap();
        map.put(ToalPages.toalPages,toalPages);
        map.put(ToalPages.totalElements,totalElements);
        map.put(ToalPages.avgAge,avgAge);
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
            user.setRank(Rank.values()[Integer.parseInt(obj[12] == null ? "" : obj[12].toString())]);
            user.setNation(new Nation(obj[13] == null ? "" : obj[13].toString()));
            user.setTel1(obj[14] == null ? "" : obj[14].toString());
            user.setTel2(obj[15] == null ? "" : obj[15].toString());
            user.setTel3(obj[16] == null ? "" : obj[16].toString());
            user.setMate(obj[17] == null ? "" : obj[17].toString());
            user.setAddress(obj[18] == null ? "" : obj[18].toString());
            user.setPolitics(new Politics(obj[19] == null ? "" : obj[19].toString()));
            user.setOther(obj[20] == null ? "" : obj[20].toString());
            if (user.getExist() == Exist.yes){
                user.setAge(obj[21] == null ? "" : obj[21].toString());
            }else if (user.getExist() == Exist.no){
                user.setAge(obj[22] == null ? "" : obj[22].toString());
            }
            lists.add(user);
        }
        return lists;
    }

    /*搜索类型*/
    enum SearchType{
        No, JobNum, Name;
    }

    enum ToalPages{
        toalPages, totalElements, avgAge;
    }

}
