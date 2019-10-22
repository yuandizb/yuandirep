package com.micro.fast.ucenter.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.ucenter.pojo.UcenterUserOauth;
import com.micro.fast.ucenter.service.UcenterUserOauthService;
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
@Api("ucenterUserOauth")
@RestController
@RequestMapping("/ucenterUserOauth")
public class UcenterUserOauthController {

  @Autowired
  private UcenterUserOauthService<UcenterUserOauth,Integer> ucenterUserOauthService;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse addUcenterUserOauth(@Valid UcenterUserOauth ucenterUserOauth) throws BindException {
    return  ucenterUserOauthService.add(ucenterUserOauth);
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping
  public ServerResponse getUcenterUserOauthById(
  @RequestParam("id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return ucenterUserOauthService.getById(Integer.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @GetMapping("/byPageCondition")
  public ServerResponse getUcenterUserOauthByCondition(UcenterUserOauth ucenterUserOauth,
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return ucenterUserOauthService.getByCondition(ucenterUserOauth,pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping
  public ServerResponse updateUcenterUserOauth(@Valid UcenterUserOauth ucenterUserOauth,
                                     @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    ucenterUserOauth.setId(Integer.valueOf(id));
    return ucenterUserOauthService.update(ucenterUserOauth);
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse deleteUcenterUserOauth(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids") @RequestParam("ids") ArrayList<Integer> ids){
    return ucenterUserOauthService.deleteByIds(ids);
  }
}
