package com.micro.fast.upms.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.upms.pojo.UpmsLog;

import java.util.List;

public interface UpmsLogMapper extends SsmMapper<UpmsLog,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(UpmsLog record);

    int insertSelective(UpmsLog record);

    UpmsLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UpmsLog record);

    int updateByPrimaryKeyWithBLOBs(UpmsLog record);

    int updateByPrimaryKey(UpmsLog record);
}