package com.example.domain.bean;

import com.example.domain.entity.ActivityDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by : Zhangxuemeng
 */
public class JoinUserSearch extends UserSearchForm{

    private String attend = "-1";

    private List<ActivityDefSearch> activityDefSearches = new ArrayList<>();

    public JoinUserSearch() {
    }

    public JoinUserSearch(List<ActivityDef> activityDefs) {
        assignActivityDefSearches(activityDefs);
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    public List<ActivityDefSearch> getActivityDefSearches() {
        return activityDefSearches;
    }

    public void setActivityDefSearches(List<ActivityDefSearch> activityDefSearches) {
        this.activityDefSearches = activityDefSearches;
    }
//    赋值给activityDefSearches
    public void assignActivityDefSearches(List<ActivityDef> activityDefs){
        if (activityDefSearches.size() != 0){
            activityDefSearches.clear();
        }
        for (ActivityDef activityDef : activityDefs) {
            ActivityDefSearch activityDefSearch = new ActivityDefSearch();
            activityDefSearch.setId(activityDef.getId());
            activityDefSearch.setLabel(activityDef.getLabel());
            activityDefSearch.setInput(activityDef.getInput());
            activityDefSearch.setInputs(activityDef.getInputs());
            activityDefSearches.add(activityDefSearch);
        }
    }
}
