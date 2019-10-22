package com.micro.fast.upms.service.impl;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.upms.dao.UpmsUserMapper;
import com.micro.fast.upms.dao.UpmsUserRoleMapper;
import com.micro.fast.upms.pojo.UpmsUser;
import com.micro.fast.upms.pojo.UpmsUserRole;
import com.micro.fast.upms.service.UpmsUserRoleService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lsy
*/
@Service
public class UpmsUserRoleServiceImpl implements UpmsUserRoleService{

  @Autowired
  private UpmsUserRoleMapper mapper;

  @Autowired
  private UpmsUserMapper upmsUserMapper;

  @Override
  public ServerResponse batchInsert(Integer userId, List<Integer> roleIds) {
    UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(userId);
    if (upmsUser == null){
      return ServerResponse.errorMsg("用户不存在");
    }
    int i = mapper.batchInsert(userId, roleIds);
    if (i != roleIds.size()){
      return ServerResponse.errorMsg("建立关系失败");
    }
    return ServerResponse.successMsg("用户添加角色成功");
  }

  @Override
  public ServerResponse batchDelete(Integer userId, List<Integer> roleIds) {
    UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(userId);
    if (upmsUser == null){
      return ServerResponse.errorMsg("用户不存在");
    }
    int i = mapper.batchDelete(userId, roleIds);
    if (i != roleIds.size()){
      return ServerResponse.errorMsg("用户删除角色失败");
    }
    return ServerResponse.successMsg("用户删除角色成功");
  }
}
