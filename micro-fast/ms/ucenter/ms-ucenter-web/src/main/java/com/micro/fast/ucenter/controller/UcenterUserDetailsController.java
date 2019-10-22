package com.micro.fast.ucenter.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.ucenter.pojo.UcenterUserDetails;
import com.micro.fast.ucenter.service.UcenterUserDetailsService;
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
@Api("ucenterUserDetails")
@RestController
@RequestMapping("/ucenterUserDetails")
public class UcenterUserDetailsController {

  @Autowired
  private UcenterUserDetailsService<UcenterUserDetails,Integer> ucenterUserDetailsService;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse addUcenterUserDetails(@Valid UcenterUserDetails ucenterUserDetails) throws BindException {
    return  ucenterUserDetailsService.add(ucenterUserDetails);
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping
  public ServerResponse getUcenterUserDetailsById(
  @RequestParam("id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return ucenterUserDetailsService.getById(Integer.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @GetMapping("/byPageCondition")
  public ServerResponse getUcenterUserDetailsByCondition(UcenterUserDetails ucenterUserDetails,
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return ucenterUserDetailsService.getByCondition(ucenterUserDetails,pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping
  public ServerResponse updateUcenterUserDetails(@Valid UcenterUserDetails ucenterUserDetails,
                                     @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    ucenterUserDetails.setId(Integer.valueOf(id));
    return ucenterUserDetailsService.update(ucenterUserDetails);
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse deleteUcenterUserDetails(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids") @RequestParam("ids") ArrayList<Integer> ids){
    return ucenterUserDetailsService.deleteByIds(ids);
  }
}
