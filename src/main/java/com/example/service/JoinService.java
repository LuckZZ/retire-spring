package com.example.service;

import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.Join;
import org.springframework.data.domain.Page;

public interface JoinService extends BaseCrudService<Join,Integer>{

    Join save(Integer userId, Integer activityId, String[] inputDefs, String attend);

    void delete(Integer[] joinIds);

    /*根据activitiyId查询已报名*/
    Page<Join> findAllByActivity_ActivityId(Integer activityId, Integer page);

    /*根据工号查询已报名*/
    Page<Join> findAllByActivity_ActivityIdAndUser_JobNum(Integer activityId, String jobNum, Integer page);

    /*根据姓名查询已报名*/
    Page<Join> findAllByActivity_ActivityIdAndUser_Name(Integer activityId, String name, Integer page);

    /*根据userSearchForm查询已报名*/
    Page<Join> findAllUserCriteria(Integer activityId, String[] inputDefs, String attend, UserSearchForm userSearchForm, Integer page);
}
