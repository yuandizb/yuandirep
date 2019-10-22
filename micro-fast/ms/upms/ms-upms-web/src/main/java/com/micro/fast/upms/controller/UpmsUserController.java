package com.micro.fast.upms.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.boot.starter.common.util.ExceptionUtil;
import com.micro.fast.upms.dao.UpmsUserMapper;
import com.micro.fast.upms.pojo.UpmsUser;
import com.micro.fast.upms.service.UpmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * 权限管理系统用户管理
 *
 * @author lsy
 */
@Api(description = "后台用户管理")
@RestController
@RequestMapping(value = "/upmsUser")
public class UpmsUserController {

    /**
     * 用户服务接口
     */
    @Autowired
    private UpmsUserService<UpmsUser, Integer> upmsUserService;

    @Autowired
    private UpmsUserMapper upmsUserMapper;

    /**
     * 用户注册,使用Valid注解对upmsUser进行参数校验
     * BindingResult一定要紧跟校验的对象
     *
     * @return
     */
    @ApiOperation(value = "添加用户")
    @PostMapping
    public ServerResponse<UpmsUser> register(@Valid UpmsUser upmsUser, @RequestParam(defaultValue = "") String repeatPassword) {
        ServerResponse<UpmsUser> register = upmsUserService.register(upmsUser, repeatPassword);
        return register;
    }

    @ApiOperation(value = "根据用户名称获取用户详细信息")
    @GetMapping("/username/{username}")
    public ServerResponse getUserByName(
            @PathVariable(name = "username") @NotNull(message = ExceptionUtil.split + "用户名不能为空") String username) {
        UpmsUser upmsUser = upmsUserMapper.selectByUsername(username);
        if (upmsUser == null) {
            return ServerResponse.errorMsg("该用户不存在!");
        }
        if (upmsUser.getLocked() == 1) {
            return ServerResponse.errorMsg("账户被锁定");
        }
        return ServerResponse.successData(upmsUser);
    }

    @ApiOperation("根据id查询详细信息")
    @GetMapping
    public ServerResponse getUserById(
            @RequestParam(value = "id") @NotBlank(message = BaseConst.BASEMSG_PREFIX + "请传入id") String id) {
        return upmsUserService.getById(Integer.valueOf(id));
    }

    @ApiOperation("根据条件分页查询")
    @GetMapping("/getPageByCondition")
    public ServerResponse getUserByCondition(UpmsUser upmsUser,
                                               @RequestParam(defaultValue = "1", required = false) int pageNum,
                                               @RequestParam(defaultValue = "10", required = false) int pageSize,
                                               @RequestParam(required = false) String orderBy) {
        return upmsUserService.getByCondition(upmsUser, pageNum, pageSize, orderBy);
    }

    @ApiOperation("修改信息")
    @PutMapping
    public ServerResponse updateUser(UpmsUser upmsUser,
                     @RequestParam("id")   @NotBlank(message = BaseConst.BASEMSG_PREFIX + "请传入id") String id) {
        upmsUser.setId(Integer.valueOf(id));
        return upmsUserService.update(upmsUser);
    }

    @ApiOperation("根据id删除，传入数组")
    @DeleteMapping
    public ServerResponse deleteUser(
            @NotEmpty(message = BaseConst.BASEMSG_PREFIX + "请传入id") @RequestParam("ids") ArrayList<Integer> ids) {
        return upmsUserService.deleteByIds(ids);
    }

    @ApiOperation("根据组织id还有用户信息查询用户")
    @GetMapping("/organization/getPageByCondition")
    public ServerResponse getUsersByOrgId(UpmsUser upmsUser,
                                          @RequestParam(defaultValue = "1", required = false) int pageNum,
                                          @RequestParam(defaultValue = "10", required = false) int pageSize,
                                          @RequestParam(required = false) String orderBy,
                                          @RequestParam(value = "organizationId",required = false) Integer orgId ) {
       return upmsUserService.getUsersByOrgId(upmsUser, orgId, pageNum, pageSize, orderBy);
    }

    @ApiOperation("获取用户的权限")
    @GetMapping("/role/permissions")
    public ServerResponse getUserPermissionByRole(@NotBlank(message = BaseConst.BASEMSG_PREFIX + "请传入用户id") String username,
                                                  @NotNull(message = BaseConst.BASEMSG_PREFIX + "请传入要查询的系统的id") Integer systemId,
                                                  @NotNull(message = BaseConst.BASEMSG_PREFIX + "请传入要查询的权限类型") Integer type){
        return upmsUserService.getUserAllPermissionByRole(username, type, systemId);
    }
}
