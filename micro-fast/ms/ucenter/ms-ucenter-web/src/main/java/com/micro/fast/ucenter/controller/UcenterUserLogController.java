package com.micro.fast.ucenter.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.ucenter.pojo.UcenterUserLog;
import com.micro.fast.ucenter.service.UcenterUserLogService;
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
@Api("ucenterUserLog")
@RestController
@RequestMapping("/ucenterUserLog")
public class UcenterUserLogController {

  @Autowired
  private UcenterUserLogService<UcenterUserLog,Integer> ucenterUserLogService;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse addUcenterUserLog(@Valid UcenterUserLog ucenterUserLog) throws BindException {
    return  ucenterUserLogService.add(ucenterUserLog);
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping
  public ServerResponse getUcenterUserLogById(
  @RequestParam("id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return ucenterUserLogService.getById(Integer.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @GetMapping("/byPageCondition")
  public ServerResponse getUcenterUserLogByCondition(UcenterUserLog ucenterUserLog,
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return ucenterUserLogService.getByCondition(ucenterUserLog,pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping
  public ServerResponse updateUcenterUserLog(@Valid UcenterUserLog ucenterUserLog,
                                     @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    ucenterUserLog.setId(Integer.valueOf(id));
    return ucenterUserLogService.update(ucenterUserLog);
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse deleteUcenterUserLog(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids") @RequestParam("ids") ArrayList<Integer> ids){
    return ucenterUserLogService.deleteByIds(ids);
  }
}
