package com.micro.fast.upms.service;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.SsmService;

/**
 * 系统管理接口
 * @author lsy
 */
public interface UpmsSystemService<T,ID> extends SsmService<T,ID>{
    /**
     * 查询系统的状态
     * @param id
     * @return
     */
    ServerResponse getSystemStatus(Integer id);
}
