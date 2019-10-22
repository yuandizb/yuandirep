package com.micro.fast.upms.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.pojo.UpmsPermission;
import com.micro.fast.upms.service.UpmsPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
*
* @author lsy
*/
@Api(description = "权限管理")
@RestController
@RequestMapping("/upmsPermission")
public class UpmsPermissionController {

  @Autowired
  private UpmsPermissionService<UpmsPermission,Integer> upmsPermissionService;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse addUpmsPermission(@Valid UpmsPermission upmsPermission) throws BindException {
    upmsPermission.setCtime(System.currentTimeMillis());
    return  upmsPermissionService.add(upmsPermission);
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping
  public ServerResponse getUpmsPermissionById(
  @PathVariable(value = "id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return upmsPermissionService.getById(Integer.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "name",value = "权限名称",dataType = "string"),
      @ApiImplicitParam(name = "systemId",value = "系统id",dataType = "integer"),
      @ApiImplicitParam(name = "status",value = "权限状态",dataType = "string")
  })
  @GetMapping("/getPageByCondition")
  public ServerResponse getUpmsPermissionByCondition(UpmsPermission upmsPermission,
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return upmsPermissionService.getByCondition(upmsPermission,pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping
  public ServerResponse updateUpmsPermission(UpmsPermission upmsPermission,
                                   @RequestParam("id")  @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    upmsPermission.setId(Integer.valueOf(id));
    return upmsPermissionService.update(upmsPermission);
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse deleteUpmsPermission(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids") @RequestParam("ids") ArrayList<Integer> ids){
    return upmsPermissionService.deleteByIds(ids);
  }

  @ApiOperation("根据用户id,权限类型,增减权限查询权限")
  @GetMapping("/permission")
  public ServerResponse getPermissions(@RequestParam("userId") Integer userId,@RequestParam("type") Integer type,@RequestParam("pType") Integer pType) {
    return  upmsPermissionService.getPermissionsByUserIdandPTypeandType(userId, type, pType);
  }

  @ApiOperation("按类型查询角色所拥有的权限")
  @GetMapping("/role/permission")
  public ServerResponse getRolePermission(@RequestParam(value = "type",required = false)  Integer type,
                                          @RequestParam("roleId") @NotNull(message = BaseConst.BASEMSG_PREFIX+"请选择角色") Integer roleId,
                                          @RequestParam(value = "systemId",required = false) Integer systemId,
                                          @RequestParam(value = "name",required = false) String name,
                                          @RequestParam(defaultValue = "1",required = false) Integer pageSize,
                                          @RequestParam(defaultValue = "10",required = false) Integer pageNum){
    return upmsPermissionService.getPermissionByRoleAndType(roleId,type,systemId,name,pageNum,pageSize);
  }

  @ApiOperation("按类型查询角色未拥有的权限")
  @GetMapping("/role/unhave/permission")
  public ServerResponse getRoleUnHavePermission(@RequestParam(value = "type",required = false)  Integer type,
                                                @RequestParam("roleId") @NotNull(message = BaseConst.BASEMSG_PREFIX+"请选择角色") Integer roleId,
                                                @RequestParam(value = "systemId",required = false) Integer systemId,
                                                @RequestParam(value = "name",required = false) String name,
                                                @RequestParam(defaultValue = "1",required = false) Integer pageSize,
                                                @RequestParam(defaultValue = "10",required = false) Integer pageNum){
    return upmsPermissionService.getUnHavePermissionByRoleIdAndTypeOrSystemIdOrPermissionName(roleId,type,systemId,name,pageNum,pageSize);
  }
}
