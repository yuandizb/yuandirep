package com.micro.fast.upms.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.upms.pojo.UpmsRole;
import org.apache.ibatis.annotations.Param;

import javax.management.relation.Role;
import java.util.List;

public interface UpmsRoleMapper extends SsmMapper<UpmsRole,Integer> {
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(UpmsRole record);

    @Override
    int insertSelective(UpmsRole record);

    @Override
    UpmsRole selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(UpmsRole record);

    @Override
    int updateByPrimaryKey(UpmsRole record);

    @Override
    List<UpmsRole> selectByCondition(UpmsRole record);

    @Override
    int deleteByPrimaryKeys(List<Integer> integers);

    /**
     * 根据用户
     * @param userId 根据用户id查询已拥有的角色
     * @return
     */
    List<UpmsRole> selectRoleJoinWithUserId(@Param("userId") Integer userId);

    /**
     * 根据角色名称查询用户未拥有的角色
     * @param userId 用户的id
     * @param roleName 角色的名称
     * @return
     */
    List<UpmsRole> selectRolesUserNotHaveByConditionPage(@Param("userId") Integer userId, @Param("roleName") String roleName);

}