package com.micro.fast.upms.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.upms.pojo.UpmsUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lsy
 */
public interface UpmsUserMapper extends SsmMapper<UpmsUser,Integer>{
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(UpmsUser record);

    @Override
    int insertSelective(UpmsUser record);

    @Override
    UpmsUser selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(UpmsUser record);

    @Override
    int updateByPrimaryKey(UpmsUser record);

    @Override
    List<UpmsUser> selectByCondition(UpmsUser record);

    @Override
    int deleteByPrimaryKeys(List<Integer> ids);

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return
     */
    UpmsUser selectByUsername(@Param("username") String username);

    /**
     * 检查用户名是否存在
     * @param username
     * @return
     */
    int countUsername(String username);

    /**
     * 检查邮箱是否被占用
     * @param email
     * @return
     */
    int countEmail(String email);

    /**
     * 根据组织id还有用户的其他信息查询
     * @return
     */
    List<UpmsUser> selectJoinWithOrgId(@Param("user") UpmsUser record, @Param("orgid") Integer orgid);
}