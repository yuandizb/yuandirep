package com.micro.fast.upms.service;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.SsmService;
import com.micro.fast.upms.pojo.UpmsUser;

/**
 * 权限管理系统用户服务接口
 * @author lsy
 */
public interface UpmsUserService<T,ID> extends SsmService<T,ID> {

  /**
   * 注册用户
   * @param upmsUser 用户对象
   * @param repeatPassword 确认密码
   * @return
   */
  ServerResponse<UpmsUser> register(UpmsUser upmsUser, String repeatPassword);

  /**
   * 检查用户名是否被占用
   * @param username 用户名
   * @return
   */
  ServerResponse checkUsername(String username);

  /**
   * 检查邮箱是否被占用
   * @param email
   * @return
   */
  ServerResponse checkEmail(String email);

  /**
   * 根据组织等信息查询用户
   */
  ServerResponse getUsersByOrgId(UpmsUser upmsUser, Integer orgId, int pageNum, int pageSize, String order_by);

  /***
   *  获取用户某个系统的视图或数据权限
   * @param username 用户名称
   * @param type 权限的类型
   * @param systemId 系统的id
   * @return
   */
   ServerResponse getUserAllPermissionByRole(String username, Integer type,Integer systemId);


  }
