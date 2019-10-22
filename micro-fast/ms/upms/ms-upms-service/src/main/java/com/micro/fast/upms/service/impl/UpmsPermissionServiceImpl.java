package com.micro.fast.upms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.upms.dao.UpmsPermissionMapper;
import com.micro.fast.upms.pojo.UpmsPermission;
import com.micro.fast.upms.service.UpmsPermissionService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lsy
*/
@Service
public class UpmsPermissionServiceImpl  extends SsmServiceImpl<UpmsPermission,Integer,UpmsPermissionMapper>
implements UpmsPermissionService<UpmsPermission,Integer>,InitializingBean {

  @Autowired
  private UpmsPermissionMapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }

  @Override
  public ServerResponse getPermissionsByUserIdandPTypeandType(Integer userId, Integer type, Integer pType) {
    List<UpmsPermission> upmsPermissions = mapper.selectByUserIdAndType(userId, pType, type);
    return ServerResponse.successMsgData("查询权限成功",upmsPermissions);
  }

  @Override
  public ServerResponse getPermissionByRoleAndType(Integer roleId, Integer type, Integer systemId, String name, Integer pageNum, Integer pageSzie) {
    PageHelper.startPage(pageNum,pageSzie,"ctime desc");
    List<UpmsPermission> upmsPermissions = mapper.selectJoinRoleByCondition(roleId, type, systemId, name);
    PageInfo pageInfo = new PageInfo(upmsPermissions);
    return ServerResponse.successData(pageInfo);
  }

  @Override
  public ServerResponse getUnHavePermissionByRoleIdAndTypeOrSystemIdOrPermissionName(Integer roleId, Integer type, Integer systemId, String name, Integer pageNum, Integer pageSize) {
      // 最近近创建的在前
      PageHelper.startPage(pageNum,pageSize,"ctime desc");
      List<UpmsPermission> upmsPermissions = mapper.selectRoleUnHavePermissionByCondition(roleId, type, systemId, name);
      PageInfo pageInfo = new PageInfo(upmsPermissions);
      return ServerResponse.successData(pageInfo);
  }
}
