package com.micro.fast.upms.service.impl;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.dao.UpmsRolePermissionMapper;
import com.micro.fast.upms.service.UpmsRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lsy
*/
@Service
public class UpmsRolePermissionServiceImpl implements UpmsRolePermissionService {

  @Autowired
  private UpmsRolePermissionMapper mapper;

  @Override
  public ServerResponse batchAddRolePermission(Integer roleId, List<Integer> permissionIds) {
    int i = mapper.batchInsert(roleId, permissionIds);
    if (i == permissionIds.size()) {
      return ServerResponse.successMsg("批量增加成功");
    }
    return ServerResponse.errorMsg("批量增加权限成功");
  }

  @Override
  public ServerResponse batchDeleteRolePermission(Integer roleId, List<Integer> permissionIds) {
    int i = mapper.batchDelete(roleId, permissionIds);
    if (i == permissionIds.size()){
      return ServerResponse.successMsg("批量删除权限成功");
    }
    return ServerResponse.errorMsg("批量删除权限失败");
  }
}
