package com.micro.fast.ucenter.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.ucenter.pojo.UcenterUserLog;

public interface UcenterUserLogMapper extends SsmMapper<UcenterUserLog,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterUserLog record);

    int insertSelective(UcenterUserLog record);

    UcenterUserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterUserLog record);

    int updateByPrimaryKeyWithBLOBs(UcenterUserLog record);

    int updateByPrimaryKey(UcenterUserLog record);
}