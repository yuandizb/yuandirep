package com.micro.fast.upms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.micro.fast.boot.starter.common.exception.SystemException;
import com.micro.fast.boot.starter.common.exception.UserException;
import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.BaseConst.SystemResponse;
import com.micro.fast.boot.starter.common.response.BaseConst.UserResponse;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.upms.dao.UpmsPermissionMapper;
import com.micro.fast.upms.dao.UpmsRoleMapper;
import com.micro.fast.upms.dao.UpmsUserMapper;
import com.micro.fast.upms.pojo.UpmsPermission;
import com.micro.fast.upms.pojo.UpmsRole;
import com.micro.fast.upms.pojo.UpmsUser;
import com.micro.fast.upms.service.UpmsUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.*;

/**
 * @author lsy
 */
@Service
public class UpmsUserServiceImpl  extends SsmServiceImpl<UpmsUser,Integer,UpmsUserMapper>
        implements UpmsUserService<UpmsUser,Integer> ,InitializingBean {


  @Autowired
  private UpmsUserMapper upmsUserMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UpmsRoleMapper upmsRoleMapper;

  @Autowired
  private UpmsPermissionMapper upmsPermissionMapper;

  @Override
  public ServerResponse<UpmsUser> register(UpmsUser upmsUser, String repeatPassword){
    ServerResponse checkUsername = checkUsername(upmsUser.getUsername());
    ServerResponse checkEmail = checkEmail(upmsUser.getEmail());
    //校验两次输入密码是否一致
    if (!upmsUser.getPassword().equals(repeatPassword)){
      throw new UserException(UserResponse.TWO_PASSWORD_NOT_SAME);
    }
    //校验用户名,邮箱是否存在
    boolean success = checkUsername.isSuccess();
    boolean success1 = checkEmail.isSuccess();
    if (!success){
      throw new UserException(UserResponse.USERNAME_IS_HAVE );
    }
    if (!success1){
      throw new UserException(UserResponse.EMAIL_IS_HAVE);
    }
    upmsUser.setCtime(System.currentTimeMillis());
    String encode = passwordEncoder.encode(upmsUser.getPassword());
    upmsUser.setPassword(encode);
    byte loacked = 0;
    upmsUser.setLocked(loacked);
    int i = upmsUserMapper.insertSelective(upmsUser);
    //是否插入成功
    if (i<1){
      throw new SystemException(SystemResponse.DATABASE_INSERT_FAILURE);
    }
    return ServerResponse.successData(upmsUser);
  }

  @Override
  public ServerResponse checkUsername(String username) {
    int i = upmsUserMapper.countUsername(username);
    if (i>0){
      //用户名被占用
      return ServerResponse.error();
    }else{
      //用户名没被占用
      return ServerResponse.success();
    }
  }

  @Override
  public ServerResponse checkEmail(String email) {
    int i = upmsUserMapper.countEmail(email);
    if (i>0){
      //邮箱已被占用
      return ServerResponse.error();
    }else{
      //邮箱没被占用
      return ServerResponse.success();
    }
  }

  @Override
  public ServerResponse getUsersByOrgId(UpmsUser upmsUser, Integer orgId, int pageNum, int pageSize, String order_by) {
    String[] split = order_by.split(BaseConst.ORDER_SPLIT);
    PageHelper.startPage(pageNum,pageSize,split[0]+BaseConst.ORDER_CONTACT+split[1]);
    List<UpmsUser> upmsUsers = upmsUserMapper.selectJoinWithOrgId(upmsUser, orgId);
    PageInfo pageInfo = new PageInfo(upmsUsers);
    return ServerResponse.successMsgData("根据组织查询用户成功",pageInfo);
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.upmsUserMapper);
  }

  /**
   * 根据用户名查找用户的所有角色下某种类型的所有权限
   * @param username 用户名
   * @param type 权限类型
   * @return
   */
  @Override
  public ServerResponse getUserAllPermissionByRole(String username, Integer type, Integer systemId){
    UpmsUser upmsUser = upmsUserMapper.selectByUsername(username);
    // 判断用户是否存在
    if (upmsUser == null){
      Map<Integer,String> map = new HashMap<>();
      map.put(UserResponse.USER_IS_NOT_FOUND.getCode(),UserResponse.USER_IS_NOT_FOUND.getMsg());
      return ServerResponse.errorMsgData("用户操作异常",map);
    }
    Integer userId = upmsUser.getId();
    List<UpmsRole> upmsRoles = upmsRoleMapper.selectRoleJoinWithUserId(userId);
    // 判断用户是否拥有权限
    if (upmsRoles.size() <= 0) {
      return ServerResponse.successMsg("当前用户没有任何角色");
    }
    // 获取用户的所有权限
    Set<UpmsPermission> permissions = new HashSet<>();
    upmsRoles.forEach(item -> {
      List<UpmsPermission> upmsPermissions = upmsPermissionMapper.selectJoinRoleByRoleIdAndTypeAndSystemIdAndStatus(item.getId(), type, systemId, 1);
      permissions.addAll(upmsPermissions);
    });
    return ServerResponse.successMsgData("获取用户的权限成功",permissions);
  }
}
