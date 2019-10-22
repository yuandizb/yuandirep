package com.micro.fast.ucenter.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.ucenter.pojo.UcenterOauth;
import com.micro.fast.ucenter.service.UcenterOauthService;
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
@Api("ucenterOauth")
@RestController
@RequestMapping("/ucenterOauth")
public class UcenterOauthController {

  @Autowired
  private UcenterOauthService<UcenterOauth,Integer> ucenterOauthService;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse addUcenterOauth(@Valid UcenterOauth ucenterOauth) throws BindException {
    return  ucenterOauthService.add(ucenterOauth);
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping
  public ServerResponse getUcenterOauthById(
  @RequestParam("id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return ucenterOauthService.getById(Integer.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @GetMapping("/byPageCondition")
  public ServerResponse getUcenterOauthByCondition(UcenterOauth ucenterOauth,
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return ucenterOauthService.getByCondition(ucenterOauth,pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping
  public ServerResponse updateUcenterOauth(@Valid UcenterOauth ucenterOauth,
                                     @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    ucenterOauth.setId(Integer.valueOf(id));
    return ucenterOauthService.update(ucenterOauth);
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse deleteUcenterOauth(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids") @RequestParam("ids") ArrayList<Integer> ids){
    return ucenterOauthService.deleteByIds(ids);
  }
}
