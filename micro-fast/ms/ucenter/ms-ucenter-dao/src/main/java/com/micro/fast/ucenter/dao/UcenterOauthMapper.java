package com.micro.fast.ucenter.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.ucenter.pojo.UcenterOauth;

public interface UcenterOauthMapper extends SsmMapper<UcenterOauth,Integer>{
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(UcenterOauth record);

    @Override
    int insertSelective(UcenterOauth record);

    @Override
    UcenterOauth selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(UcenterOauth record);

    @Override
    int updateByPrimaryKey(UcenterOauth record);
}