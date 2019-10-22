package com.micro.fast.upms.service;

import com.micro.fast.boot.starter.common.response.ServerResponse;

import java.util.List;

/**
* @author lsy
*/
public interface UpmsUserRoleService{

    /**
     * 批量建立用户和角色的关系
     * @param userId 用户id
     * @param roleIds 角色id的集合
     * @return
     */
    ServerResponse batchInsert(Integer userId, List<Integer> roleIds);

    /**
     * 批量解除用户和角色的关系
     * @param userId 用户id
     * @param roleIds 角色id的集合
     * @return
     */
    ServerResponse batchDelete(Integer userId, List<Integer> roleIds);
}