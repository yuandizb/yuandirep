package com.micro.fast.upms.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.pojo.UpmsLog;
import com.micro.fast.upms.service.UpmsLogService;
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
@Api("upmsLog")
@RestController
@RequestMapping("/upmsLog")
public class UpmsLogController {

  @Autowired
  private UpmsLogService<UpmsLog,Integer> upmsLogService;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse addUpmsLog(@Valid UpmsLog upmsLog) throws BindException {
    return  upmsLogService.add(upmsLog);
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping("/{id}")
  public ServerResponse getUpmsLogById(
  @PathVariable(value = "id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return upmsLogService.getById(Integer.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @GetMapping
  public ServerResponse getUpmsLogByCondition(UpmsLog upmsLog,
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return upmsLogService.getByCondition(upmsLog,pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping("/{id}")
  public ServerResponse updateUpmsLog(UpmsLog upmsLog,
                                     @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    upmsLog.setId(Integer.valueOf(id));
    return upmsLogService.update(upmsLog);
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse deleteUpmsLog(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids")
                                            @RequestParam("ids") ArrayList<Integer> ids){
    return upmsLogService.deleteByIds(ids);
  }
}
