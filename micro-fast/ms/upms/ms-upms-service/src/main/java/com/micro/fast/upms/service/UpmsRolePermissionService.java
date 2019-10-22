package com.micro.fast.upms.service;

import com.micro.fast.boot.starter.common.response.ServerResponse;

import java.util.List;

/**
 * 角色权限管理接口
* @author lsy
*/
public interface UpmsRolePermissionService{

    /**
     * 批量增加角色权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    ServerResponse batchAddRolePermission(Integer roleId,List<Integer> permissionIds);

    /**
     * 批量删除角色权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    ServerResponse batchDeleteRolePermission(Integer roleId,List<Integer> permissionIds);
}