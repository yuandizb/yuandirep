package com.micro.fast.upms.controller;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.service.UpmsUserOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
*
* @author lsy
*/
@Api(description = "用户组织关系维护")
@RestController
@RequestMapping("/upmsUserOrganization")
public class UpmsUserOrganizationController {

  @Autowired
  private UpmsUserOrganizationService upmsUserOrganizationService;

  @ApiOperation("批量添加用户和组织的关系")
  @PostMapping("/batch")
  public ServerResponse batchAdd(@RequestParam("userId") Integer userId,
                                 @RequestParam("organizationIds") ArrayList<Integer> organizationIds){
    return upmsUserOrganizationService.batchInsert(userId,organizationIds);
  }
  @ApiOperation("批量删除用户和组织的关系")
  @DeleteMapping("/batch")
  public ServerResponse batchDelete(@RequestParam("userId") Integer userId,
                                    @RequestParam("organizationIds") ArrayList<Integer> organizationIds){
    return upmsUserOrganizationService.batchDelete(userId,organizationIds);
  }
}
