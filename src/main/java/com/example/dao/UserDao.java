package com.example.dao;

import com.example.domain.entity.Group;
import com.example.domain.entity.User;
import com.example.domain.enums.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    boolean existsByJobNum(String jobNum);

    List<User> findAllByJobNum(String jobNum);

    List<User> findAllByName(String name);

    @Modifying(clearAutomatically=true)
    @Query("update User set rank=:rank where userId=:userId")
    int updateRank(@Param("rank") Rank rank, @Param("userId") Integer userId);

    long countByGroup(Group group);

    List<User> findAllByGroupAndRank(Group group, Rank rank);

    List<User> findAllByGroup(Group group);

    @Modifying(clearAutomatically=true)
    @Query(value = "update tb_user set group_id=?1 where group_id=?2", nativeQuery = true)
    int updateGroup(Integer newGroupId, Integer oldGroupId);

    @Modifying(clearAutomatically=true)
    @Query(value = "update tb_user set group_id=?1 where user_id=?2", nativeQuery = true)
    int updateGroupByUseId(Integer groupId, Integer userId);

}
