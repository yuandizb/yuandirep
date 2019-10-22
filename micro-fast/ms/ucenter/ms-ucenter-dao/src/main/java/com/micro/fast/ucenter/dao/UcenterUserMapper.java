package com.micro.fast.ucenter.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.ucenter.pojo.UcenterUser;

public interface UcenterUserMapper  extends SsmMapper<UcenterUser,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterUser record);

    int insertSelective(UcenterUser record);

    UcenterUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterUser record);

    int updateByPrimaryKey(UcenterUser record);
}