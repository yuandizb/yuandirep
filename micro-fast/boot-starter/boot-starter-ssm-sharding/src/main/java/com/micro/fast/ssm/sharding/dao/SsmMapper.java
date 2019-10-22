package com.micro.fast.ssm.sharding.dao;

import java.util.List;

public interface SsmMapper<T,ID> {

    int deleteByPrimaryKey(ID id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(ID id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKeyWithBLOBs(T record);

    int updateByPrimaryKey(T record);

    /**
     * 按条件分页查询
     * @param record
     * @return
     */
    List<T> selectByCondition(T record);

    int deleteByPrimaryKeys(List<ID> id);
}
