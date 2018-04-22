package com.example.service;

import com.example.domain.entity.Join;
import org.springframework.data.domain.Page;

public interface JoinService extends BaseCrudService<Join,Integer>{

    Join save(Integer userId, Integer activityId, String[] inputDefs, String attend);

    Page<Join> findAllByActivity_ActivityId(Integer activityId, Integer page);

    void delete(Integer[] joinIds);

}
