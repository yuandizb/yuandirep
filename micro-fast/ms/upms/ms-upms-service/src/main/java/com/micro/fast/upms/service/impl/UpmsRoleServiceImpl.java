package com.micro.fast.upms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.upms.dao.UpmsRoleMapper;
import com.micro.fast.upms.pojo.UpmsRole;
import com.micro.fast.upms.service.UpmsRoleService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

/**
* @author lsy
*/
@Service
public class UpmsRoleServiceImpl  extends SsmServiceImpl<UpmsRole,Integer,UpmsRoleMapper>
implements UpmsRoleService<UpmsRole,Integer>,InitializingBean {

  @Autowired
  private UpmsRoleMapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }

  @Override
  public ServerResponse getRolesByUserId(Integer useId) {
    List<UpmsRole> roles = mapper.selectRoleJoinWithUserId(useId);
    return ServerResponse.successMsgData("根据用户id查询角色成功",roles);
  }

  @Override
  public ServerResponse selectRolesUserNotHaveByConditionPage(Integer userId, String roleName, int pageSize, int pageNum) {
    PageHelper.startPage(pageNum,pageSize,"ctime_desc");
    List<UpmsRole> roles = mapper.selectRolesUserNotHaveByConditionPage(userId, roleName);
    PageInfo pageInfo = new PageInfo(roles);
    return ServerResponse.successData(pageInfo);
  }
}
