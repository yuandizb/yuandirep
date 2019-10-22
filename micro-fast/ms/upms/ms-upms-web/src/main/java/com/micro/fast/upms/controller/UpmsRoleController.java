package com.micro.fast.upms.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.pojo.UpmsRole;
import com.micro.fast.upms.service.UpmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.ArrayList;

/**
*
* @author lsy
*/
@Api(description = "角色管理")
@RestController
@RequestMapping("/upmsRole")
public class UpmsRoleController {

  @Autowired
  private UpmsRoleService<UpmsRole,Integer> upmsRoleService;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse addUpmsRole(@Valid UpmsRole upmsRole) throws BindException {
    upmsRole.setCtime(System.currentTimeMillis());
    return  upmsRoleService.add(upmsRole);
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping
  public ServerResponse getUpmsRoleById(
  @RequestParam(value = "id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return upmsRoleService.getById(Integer.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @GetMapping("/getPageByCondition")
  public ServerResponse getUpmsRoleByCondition(UpmsRole upmsRole,
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return upmsRoleService.getByCondition(upmsRole,pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping
  public ServerResponse updateUpmsRole(UpmsRole upmsRole,
                                  @RequestParam("id")   @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    upmsRole.setId(Integer.valueOf(id));
    return upmsRoleService.update(upmsRole);
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse deleteUpmsRole(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids") @RequestParam("ids") ArrayList<Integer> ids){
    return upmsRoleService.deleteByIds(ids);
  }

  /**
   * 根据用户的id 查询用户所拥有的角色
   * @param userId
   * @return
   */
  @ApiOperation("根据用户id查询角色")
  @GetMapping("/userId")
  public ServerResponse getRolesByUserId(@RequestParam("userId") Integer userId){
    return upmsRoleService.getRolesByUserId(userId);
  }

  /**
   * @param userId 用户的id
   * @param roleName 角色名称
   * @param pageNum
   * @param pageSize
   * @return
   */
  @ApiOperation("根据用户id和角色名称查询角色")
  @GetMapping("/selectRolesUserNotHaveByConditionPage")
  public ServerResponse selectRolesUserNotHaveByConditionPage(@RequestParam("userId") @NotNull Integer userId,
                                                              @RequestParam(value = "roleName",required = false) String roleName,
                                                              @RequestParam(required = false,defaultValue = "1") int pageNum,
                                                              @RequestParam(required = false,defaultValue = "10") int pageSize){
    return upmsRoleService.selectRolesUserNotHaveByConditionPage(userId, roleName, pageSize, pageNum);
  }

}
