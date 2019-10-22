package com.micro.fast.upms.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.service.UpmsRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * 角色权限管理
* @author lsy
*/
@Api("角色权限管理")
@RestController
@RequestMapping("/upmsRolePermission")
public class UpmsRolePermissionController {

  @Autowired
  private UpmsRolePermissionService upmsRolePermissionService;

  @ApiOperation("给角色批量添加权限")
  @PostMapping("/addRolePermission")
  public ServerResponse addRolePermission(@RequestParam("roleId") @NotNull(message = BaseConst.BASEMSG_PREFIX+"请传入角色id") Integer roleId,
                                          @RequestParam("permissionIds") @NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入权限id的集合") ArrayList<Integer> permissionIds){
    return upmsRolePermissionService.batchAddRolePermission(roleId,permissionIds);
  }
  @ApiOperation("给角色批量删除权限")
  @DeleteMapping("/deleteRolePermission")
  public ServerResponse deleteRolePermission(@RequestParam("roleId") @NotNull(message = BaseConst.BASEMSG_PREFIX+"请传入角色id") Integer roleId,
                                          @RequestParam("permissionIds") @NotEmpty ArrayList<Integer> permissionIds){
    return upmsRolePermissionService.batchDeleteRolePermission(roleId,permissionIds);
  }
}
