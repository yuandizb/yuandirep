package com.micro.fast.ucenter.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.ucenter.pojo.UcenterUser;
import com.micro.fast.ucenter.service.UcenterUserService;
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
* @author lsy
*/
@Api("ucenterUser")
@RestController
@RequestMapping("/ucenterUser")
public class UcenterUserController {

  @Autowired
  private UcenterUserService<UcenterUser,Integer> ucenterUserService;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse addUcenterUser(@Valid UcenterUser ucenterUser) throws BindException {
    return  ucenterUserService.add(ucenterUser);
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping
  public ServerResponse getUcenterUserById(
  @RequestParam("id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return ucenterUserService.getById(Integer.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @GetMapping("/byPageCondition")
  public ServerResponse getUcenterUserByCondition(UcenterUser ucenterUser,
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return ucenterUserService.getByCondition(ucenterUser,pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping
  public ServerResponse updateUcenterUser(@Valid UcenterUser ucenterUser,
                                     @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    ucenterUser.setId(Integer.valueOf(id));
    return ucenterUserService.update(ucenterUser);
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse deleteUcenterUser(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids") @RequestParam("ids") ArrayList<Integer> ids){
    return ucenterUserService.deleteByIds(ids);
  }
}
