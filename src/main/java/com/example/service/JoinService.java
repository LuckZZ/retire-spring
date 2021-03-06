package com.example.service;

import com.example.domain.bean.JoinBean;
import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.Join;
import com.example.domain.enums.JoinStatus;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface JoinService extends BaseCrudService<Join,Integer>{

    /*根据activityId和userId，查看Join是否存在此条数据*/
    boolean existsByActivityIdAndUserId(Integer activityId, Integer userId);

    /*根据activityId和userIds，查看userIds是否都在join表中*/
    boolean existsByActivityIdAndUserId(Integer activityId, Integer[] userIds);

    /*根据joinId和groupId，查看Join是否存在此条数据*/
    boolean existsByJoinIdAndGroupId(Integer[] joinIds, Integer groupId, JoinStatus joinStatus);

    /*保存为草稿*/
    Join saveDraft(Integer userId, Integer activityId, String[] inputDefs, String attend, String other);

    /*批量保存为草稿*/
    boolean saveDraft(List<JoinBean> joinBeansList);

    /*保存为提交*/
    Join saveUltima(Integer userId, Integer activityId, String[] inputDefs, String attend, String other);

    /*批量保存为提交*/
    boolean saveUltima(List<JoinBean> joinBeansList);

    void delete(Integer[] joinIds);

    /*根据工号查询已报名*/
    Page<Join> findAllByActivityIdAndJobNum(Integer activityId, String jobNum, JoinStatus joinStatus, Integer page);

    List<Join> findAllByActivityIdAndJobNum(Integer activityId, String jobNum, JoinStatus joinStatus);

    /*根据姓名查询已报名*/
    Page<Join> findAllByActivityIdAndNameContaining(Integer activityId, String name, JoinStatus joinStatus, Integer page);

    List<Join> findAllByActivityIdAndNameContaining(Integer activityId, String name, JoinStatus joinStatus);

    /*根据工号、组Id查询已报名*/
    Page<Join> findAllByActivityIdAndJobNumWithGroupId(Integer activityId, String jobNum, Integer groupId, JoinStatus joinStatus, Integer page);

    List<Join> findAllByActivityIdAndJobNumWithGroupId(Integer activityId, String jobNum, Integer groupId, JoinStatus joinStatus);

    /*根据姓名、组Id查询已报名*/
    Page<Join> findAllByActivityIdAndNameContainingWithGroupId(Integer activityId, String name, Integer groupId, JoinStatus joinStatus, Integer page);

    List<Join> findAllByActivityIdAndNameContainingWithGroupId(Integer activityId, String name, Integer groupId, JoinStatus joinStatus);

    /*根据userSearchForm查询已报名*/
    Page<Join> findAllCriteria(Integer activityId, String[] inputDefs, String attend, JoinStatus joinStatus, UserSearchForm userSearchForm, Integer page);

    List<Join> findAllCriteria(Integer activityId, String[] inputDefs, String attend, JoinStatus joinStatus, UserSearchForm userSearchForm);

    /*根据活动id，用户id，活动状态查询*/
    List<Join> findAllByActivityIdAndUserIdAndJoinStatus(Integer activityId, Integer userId, JoinStatus joinStatus);

    /*根据joinIds数组查询*/
    List<Join> findAllByJoinIds(Integer[] joinIds);

}
