package com.micro.fast.ssm.sharding.service;

import com.micro.fast.boot.starter.common.response.ServerResponse;

import java.util.List;

/**
 * ssm框架service基础操作接口
 * @author lsy
 */
public interface SsmService<T,ID> {
    /**
     * 增加
     */
    ServerResponse add(T pojo);
    /**
     * 根据id查询
     */
    ServerResponse getById(ID id);
    /**
     * 按条件分页查询
     */
     ServerResponse getByCondition(T pojo,int pageNum,int pageSize,String orderBy);
    /**
     * 修改
     */
    ServerResponse update(T pojo);
    /**
     * 根据id删除单个
     */
    ServerResponse deleteById(ID id);

    /**
     * 根据id删除多个
     */
    ServerResponse deleteByIds(List<ID> ids);
}
