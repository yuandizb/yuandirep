package com.micro.fast.upms.service;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.SsmService;

/**
* @author lsy
*/
public interface UpmsRoleService<T,ID> extends SsmService<T,ID>{
  /**
   * 查询用户已拥有的角色
   * @param useId
   * @return
   */
  ServerResponse getRolesByUserId(Integer useId);

  /**
   * 根据用户id和角色名称查询用户所拥有的角色
   * @param userId 用户的id
   * @param roleName 角色名称
   * @param pageSize 单页的数量
   * @param pageNum 页码
   * @return
   */
  ServerResponse selectRolesUserNotHaveByConditionPage(Integer userId, String roleName, int pageSize, int pageNum);
}