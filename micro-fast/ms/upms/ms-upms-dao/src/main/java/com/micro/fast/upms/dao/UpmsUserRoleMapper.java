package com.micro.fast.upms.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpmsUserRoleMapper {

    /**
     * 批量建立用户和角色的关系
     * @param userId 用户的id
     * @param roleIds 角色的id集合
     * @return
     */
    int batchInsert(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);

    /**
     * 批量删除用户和角色的关系
     * @param userId 用户的id
     * @param roleIds 角色的id集合
     * @return
     */
    int batchDelete(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);
}