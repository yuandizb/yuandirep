package com.micro.fast.upms.controller;

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.dao.UpmsSystemMapper;
import com.micro.fast.upms.pojo.UpmsSystem;
import com.micro.fast.upms.service.UpmsSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * 系统管理
 * @author lsy
 */
@Api(description = "系统管理")
@RestController
@RequestMapping("/upmsSystem")
public class UpmsSystemController {

    @Autowired
    private UpmsSystemService<UpmsSystem,Integer> upmsSystemService;

    @Autowired
    private UpmsSystemMapper upmsSystemMapper;

    @ApiOperation("添加信息")
    @PostMapping
    public ServerResponse addSystem(@Valid UpmsSystem upmsSystem) throws BindException {
        upmsSystem.setCtime(System.currentTimeMillis());
        if (upmsSystem.getStatus() == null){
            upmsSystem.setStatus((byte)1);
        }
        return  upmsSystemService.add(upmsSystem);
    }

    @ApiOperation("根据id查询详细信息")
    @GetMapping
    public ServerResponse getSystemById(
            @RequestParam(value = "id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
        return upmsSystemService.getById(Integer.valueOf(id));
    }

    @ApiOperation("根据条件分页查询")
    @GetMapping("/getPageByCondition")
    public ServerResponse getSystemByCondition(UpmsSystem upmsSystem,
                                               @RequestParam(defaultValue = "1",required = false) int pageNum,
                                               @RequestParam(defaultValue = "10",required = false)int pageSize,
                                               @RequestParam(required = false) String orderBy){
        return upmsSystemService.getByCondition(upmsSystem,pageNum,pageSize,orderBy);
    }

    @ApiOperation("修改信息")
    @PutMapping
    public ServerResponse updateSystem(UpmsSystem upmsSystem){
        return upmsSystemService.update(upmsSystem);
    }

    @ApiOperation("根据id删除，传入数组")
    @DeleteMapping
    public ServerResponse deleteSystem(
            @NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入id") @RequestParam("ids") ArrayList<Integer> ids){
        return upmsSystemService.deleteByIds(ids);
    }

    @ApiOperation("根据id查询系统的状态")
    @GetMapping("/getSystemStatusBySystemId")
    public ServerResponse getSystemStatusBySystemId(
            @NotNull(message = BaseConst.BASEMSG_PREFIX + "请传入系统的id") @RequestParam("systemId") Integer systemId){
        return upmsSystemService.getSystemStatus(systemId);
    }

    @ApiOperation("根据系统路由查找系统的状态")
    @GetMapping("/getSystemStatusByRoute")
    public ServerResponse getSystemStatusByRoute(
                @NotEmpty(message = BaseConst.BASEMSG_PREFIX + "请传入系统的路由") @RequestParam("route") String route){
        int i = upmsSystemMapper.selectSystemStatusByRoute(route);
        return ServerResponse.successData(i);
    }

    @ApiOperation("根据系统路由查询系统的id")
    @GetMapping("/getSystemIdByRoute")
    public ServerResponse getSystemIdByRoute(@NotEmpty(message = BaseConst.BASEMSG_PREFIX + "请传入系统的路由") @RequestParam("route") String route){
        int i = upmsSystemMapper.selectSystemIdByRoute(route);
        return ServerResponse.successData(i);
    }
}
