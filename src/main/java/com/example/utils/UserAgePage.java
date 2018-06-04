package com.example.utils;

import com.example.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 类似Page<T>
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public class UserAgePage {
//  数据
    private List<User> content = new ArrayList<>();
//    总页数
    private int totalPages;
    //    总条数
    private int totalElements;
//    当前页数
    private int number;
//    平均年龄
    private int avgAge;

    public List<User> getContent() {
        return content;
    }

    public void setContent(List<User> content) {
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAvgAge() {
        return avgAge;
    }

    public void setAvgAge(int avgAge) {
        this.avgAge = avgAge;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
