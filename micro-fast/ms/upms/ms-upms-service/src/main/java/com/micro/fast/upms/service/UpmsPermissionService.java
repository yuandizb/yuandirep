package com.micro.fast.upms.service;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.SsmService;

/**
* @author lsy
*/
public interface UpmsPermissionService<T,ID> extends SsmService<T,ID>{

  @Deprecated
  ServerResponse getPermissionsByUserIdandPTypeandType(Integer userId, Integer type, Integer pType);


  /**
   * 按照类型获取角色所拥有的权限
   * @param type 权限类型
   * @param roleId 角色id
   * @return
   */
  ServerResponse getPermissionByRoleAndType(Integer roleId, Integer type, Integer systemId, String name, Integer pageNum, Integer pageSzie);

  /**
   * 根据权限的类型，权限所属系统，权限的名称查询角色未拥有的权限
   * @param roleId 角色的id
   * @param type 权限的类型
   * @param systemId 系统的id
   * @param name 权限的名称
   * @return
   */
  ServerResponse getUnHavePermissionByRoleIdAndTypeOrSystemIdOrPermissionName(Integer roleId, Integer type, Integer systemId, String name, Integer pageNum, Integer pageSzie);
}