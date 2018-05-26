package com.example.service;

import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.Join;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface JoinService extends BaseCrudService<Join,Integer>{

    /*根据activityId和userId，查看Join是否存在此条数据*/
    boolean existsByActivityIdAndUserId(Integer activityId, Integer userId);

    /*根据joinId和groupId，查看Join是否存在此条数据*/
    boolean existsByJoinIdAndGroupId(Integer[] joinIds, Integer groupId);

    Join save(Integer userId, Integer activityId, String[] inputDefs, String attend, String other);

    void delete(Integer[] joinIds);

    /*根据工号查询已报名*/
    Page<Join> findAllByActivityIdAndJobNum(Integer activityId, String jobNum, Integer page);

    List<Join> findAllByActivityIdAndJobNum(Integer activityId, String jobNum);

    /*根据姓名查询已报名*/
    Page<Join> findAllByActivityIdAndName(Integer activityId, String name, Integer page);

    List<Join> findAllByActivityIdAndName(Integer activityId, String name);

    /*根据工号、组Id查询已报名*/
    Page<Join> findAllByActivityIdAndJobNumWithGroupId(Integer activityId, String jobNum, Integer groupId, Integer page);

    List<Join> findAllByActivityIdAndJobNumWithGroupId(Integer activityId, String jobNum, Integer groupId);

    /*根据姓名、组Id查询已报名*/
    Page<Join> findAllByActivityIdAndNameWithGroupId(Integer activityId, String name, Integer groupId, Integer page);

    List<Join> findAllByActivityIdAndNameWithGroupId(Integer activityId, String name, Integer groupId);

    /*根据userSearchForm查询已报名*/
    Page<Join> findAllCriteria(Integer activityId, String[] inputDefs, String attend, UserSearchForm userSearchForm, Integer page);

    List<Join> findAllCriteria(Integer activityId, String[] inputDefs, String attend, UserSearchForm userSearchForm);

    /*根据joinIds数组查询*/
    List<Join> findAllByJoinIds(Integer[] joinIds);

}
