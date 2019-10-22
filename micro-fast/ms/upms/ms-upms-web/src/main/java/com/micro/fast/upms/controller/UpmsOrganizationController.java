package com.micro.fast.upms.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.pojo.UpmsOrganization;
import com.micro.fast.upms.service.UpmsOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
*
* @author lsy
*/
@Api(description = "权限系统组织管理")
@RestController
@RequestMapping("/upmsOrganization")
public class UpmsOrganizationController {

  @Autowired
  private UpmsOrganizationService<UpmsOrganization,Integer> upmsOrganizationService;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse addUpmsOrganization(@Valid UpmsOrganization upmsOrganization) throws BindException {
    upmsOrganization.setCtime(System.currentTimeMillis());
    return  upmsOrganizationService.add(upmsOrganization);
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping
  public ServerResponse getUpmsOrganizationById(
  @RequestParam("id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return upmsOrganizationService.getById(Integer.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @GetMapping("/getPageByCondition")
  public ServerResponse getUpmsOrganizationByCondition(UpmsOrganization upmsOrganization,
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return upmsOrganizationService.getByCondition(upmsOrganization,pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping
  public ServerResponse updateUpmsOrganization(@Valid UpmsOrganization upmsOrganization,
                                    @RequestParam("id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    upmsOrganization.setId(Integer.valueOf(id));
    return upmsOrganizationService.update(upmsOrganization);
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse deleteUpmsOrganization(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids") @RequestParam("ids") ArrayList<Integer> ids){
    return upmsOrganizationService.deleteByIds(ids);
  }

  @ApiOperation("根据用户id查询组织")
  @GetMapping("/organization")
  public ServerResponse getOrganization(@RequestParam("userId") Integer userId) {
    return upmsOrganizationService.getOrganizationByUserId(userId);
  }
}
