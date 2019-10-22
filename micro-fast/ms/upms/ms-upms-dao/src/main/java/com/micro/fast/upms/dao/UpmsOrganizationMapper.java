package com.micro.fast.upms.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.upms.pojo.UpmsOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpmsOrganizationMapper extends SsmMapper<UpmsOrganization,Integer> {
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(UpmsOrganization record);

    @Override
    int insertSelective(UpmsOrganization record);

    @Override
    UpmsOrganization selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(UpmsOrganization record);

    @Override
    int updateByPrimaryKey(UpmsOrganization record);

    @Override
    List<UpmsOrganization> selectByCondition(UpmsOrganization record);

    @Override
    int deleteByPrimaryKeys(List<Integer> integers);

    List<UpmsOrganization> selectJionWithUserId(@Param("userId") Integer userId);
}