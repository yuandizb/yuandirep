package com.micro.fast.upms.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.service.UpmsUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
*
* @author lsy
*/
@Api("用户和角色关系维护")
@RestController
@RequestMapping("/upmsUserRole")
public class UpmsUserRoleController {

  @Autowired
  private UpmsUserRoleService upmsUserRoleService;


  @ApiOperation("用户添加角色")
  @PostMapping("/userAddRoles")
  public ServerResponse userAddRoles(@RequestParam("userId") @NotNull(message = BaseConst.BASEMSG_PREFIX+"请传入用户的id") Integer userId,
                                     @RequestParam("roleIds")
                                     @NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入的角色的id")  ArrayList<Integer> roleIds){
    return upmsUserRoleService.batchInsert(userId,roleIds);
  }

  @ApiOperation("用户删除角色")
  @DeleteMapping("/userDeleteRoles")
  public ServerResponse userDeleteRoles(@RequestParam("userId") @NotNull(message = BaseConst.BASEMSG_PREFIX+"请传入用户的id") Integer userId,
                                     @RequestParam("roleIds")
                                     @NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入的角色的id")  ArrayList<Integer> roleIds){
    return upmsUserRoleService.batchDelete(userId,roleIds);
  }
}
