package com.micro.fast.ucenter.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.ucenter.pojo.UcenterUserOauth;

public interface UcenterUserOauthMapper extends SsmMapper<UcenterUserOauth,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterUserOauth record);

    int insertSelective(UcenterUserOauth record);

    UcenterUserOauth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterUserOauth record);

    int updateByPrimaryKeyWithBLOBs(UcenterUserOauth record);

    int updateByPrimaryKey(UcenterUserOauth record);
}