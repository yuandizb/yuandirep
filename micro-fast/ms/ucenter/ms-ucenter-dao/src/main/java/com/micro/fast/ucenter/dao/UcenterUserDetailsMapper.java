package com.micro.fast.ucenter.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.ucenter.pojo.UcenterUserDetails;

public interface UcenterUserDetailsMapper extends SsmMapper<UcenterUserDetails,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterUserDetails record);

    int insertSelective(UcenterUserDetails record);

    UcenterUserDetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterUserDetails record);

    int updateByPrimaryKey(UcenterUserDetails record);
}