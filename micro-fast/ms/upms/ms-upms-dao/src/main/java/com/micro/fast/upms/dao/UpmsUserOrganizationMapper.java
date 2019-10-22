package com.micro.fast.upms.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpmsUserOrganizationMapper {

    /**
     * 批量插入一个用户和多个组织的关系
     * @param userId
     * @param orgIds
     * @return
     */
    int batchInsertOneUserWithManyOrg(@Param("userId") Integer userId, @Param("orgIds") List<Integer> orgIds);

    /**
     * 批量删除一个用户和多个组织的关系
     * @param userId
     * @param orgs
     * @return
     */
    int batchDeleteByUserIdOrganizationId(@Param("userId") Integer userId, @Param("orgIds") List<Integer> orgs);
}