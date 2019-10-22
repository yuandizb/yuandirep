package com.micro.fast.upms.dao;

import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.upms.pojo.UpmsSystem;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UpmsSystemMapper extends SsmMapper<UpmsSystem,Integer> {

    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(UpmsSystem record);

    @Override
    int insertSelective(UpmsSystem record);

    @Override
    UpmsSystem selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(UpmsSystem record);

    @Override
    int updateByPrimaryKey(UpmsSystem record);

    @Override
    List<UpmsSystem> selectByCondition(UpmsSystem record);

    @Override
    int deleteByPrimaryKeys(List<Integer> ids);

    /**
     * 查询某个系统的状态,必要时对系统进行关闭
     * @param id 系统的id
     * @return
     */
    @Select("select status from upms_system where id = #{id}")
    int selectSystemStatus(Integer id);

    @Select("select status from upms_system where route = #{route}")
    int selectSystemStatusByRoute(String route);

    /**
     * 根据系统路由查询系统id
     * @param route
     * @return
     */
    @Select("select id from upms_system where route = #{route}")
    int selectSystemIdByRoute(String route);
}