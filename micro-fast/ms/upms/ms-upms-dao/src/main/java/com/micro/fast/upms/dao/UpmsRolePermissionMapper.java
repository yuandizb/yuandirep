package com.micro.fast.upms.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.upms.pojo.UpmsRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpmsRolePermissionMapper extends SsmMapper<UpmsRolePermission,Integer> {
    /**
     * 批量增加角色的权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    int batchInsert(@Param("roleId") Integer roleId,@Param("permissionIds") List<Integer> permissionIds);

    /**
     * 批量删除角色的权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    int batchDelete(@Param("roleId") Integer roleId,@Param("permissionIds") List<Integer> permissionIds);

}