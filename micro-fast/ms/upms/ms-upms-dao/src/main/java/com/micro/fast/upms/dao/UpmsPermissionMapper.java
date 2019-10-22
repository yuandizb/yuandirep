package com.micro.fast.upms.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.upms.pojo.UpmsPermission;
import com.micro.fast.upms.pojo.UpmsSystem;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UpmsPermissionMapper extends SsmMapper<UpmsPermission,Integer> {
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(UpmsPermission record);

    @Override
    int insertSelective(UpmsPermission record);

    @Override
    UpmsPermission selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(UpmsPermission record);

    @Override
    int updateByPrimaryKey(UpmsPermission record);

    @Override
    List<UpmsPermission> selectByCondition(UpmsPermission record);

    @Override
    int deleteByPrimaryKeys(List<Integer> integers);


    @Deprecated
    List<UpmsPermission> selectByUserIdAndType(@Param("userId") Integer userId, @Param("pType") Integer pType, @Param("type") Integer type);

    /**
     * 查询角色已拥有的角色
     *
     * @param roleId
     * @param type
     * @return
     */
    List<UpmsPermission> selectJoinRoleByCondition(@Param("roleId") Integer roleId,
                                                   @Param("type") Integer type,
                                                   @Param("systemId") Integer systemId,
                                                   @Param("name") String name);

    /**
     * 根据角色id，系统id, 权限类型，权限状态查询
     * @param roleId 角色id
     * @param type 权限类型
     * @param systemId 系统id
     * @param status 系统状态
     * @return
     */
    List<UpmsPermission> selectJoinRoleByRoleIdAndTypeAndSystemIdAndStatus(@Param("roleId") Integer roleId,
                                                                           @Param("type") Integer type,
                                                                           @Param("systemId") Integer systemId,
                                                                           @Param("status") Integer status);

    /**
     * 分页查询角色未拥有的角色
     *
     * @param roleId   角色id
     * @param type     权限的类型
     * @param systemId 系统的id
     * @param name     权限的名称
     * @return
     */
    List<UpmsPermission> selectRoleUnHavePermissionByCondition(@Param("roleId") Integer roleId,
                                                               @Param("type") Integer type,
                                                               @Param("systemId") Integer systemId,
                                                               @Param("name") String name);
}

