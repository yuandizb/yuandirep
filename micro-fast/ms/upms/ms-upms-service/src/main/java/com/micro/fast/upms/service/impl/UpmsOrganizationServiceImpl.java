package com.micro.fast.upms.service.impl;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.upms.dao.UpmsOrganizationMapper;
import com.micro.fast.upms.pojo.UpmsOrganization;
import com.micro.fast.upms.service.UpmsOrganizationService;
import org.apache.http.protocol.ResponseServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lsy
*/
@Service
public class UpmsOrganizationServiceImpl  extends SsmServiceImpl<UpmsOrganization,Integer,UpmsOrganizationMapper>
implements UpmsOrganizationService<UpmsOrganization,Integer>,InitializingBean {

  @Autowired
  private UpmsOrganizationMapper mapper;

  @Override
  public ServerResponse add(UpmsOrganization pojo) {
    if (pojo.getPid().equals(pojo.getId())) {
      return ServerResponse.errorMsg("不予许以自身为父节点");
    }
    if (pojo.getPid() == 0) {
      // 如果是创建根节点就检查根节点是否已经被创建
      UpmsOrganization upmsOrganization = new UpmsOrganization();
      upmsOrganization.setPid(0);
      List<UpmsOrganization> upmsOrganizations = mapper.selectByCondition(upmsOrganization);
      if (upmsOrganizations.size() == 0){
        return super.add(pojo);
      } else {
        return ServerResponse.errorMsg("根节点已经被创建");
      }
    } else {
      // 如果不是根节点就查询父节点是否存在
      UpmsOrganization upmsOrganization = mapper.selectByPrimaryKey(pojo.getPid());
      if (upmsOrganization == null){
        return ServerResponse.errorMsg("上级组织不存在,请重新输入上级组织id");
      } else {
        // 添加添加组织
        return super.add(pojo);
      }
    }
  }

  @Override
  public ServerResponse update(UpmsOrganization pojo) {
    if (pojo.getPid().equals(pojo.getId())) {
      return ServerResponse.errorMsg("不予许以自身为父节点");
    }
    if ( pojo.getPid() == 0) {
      UpmsOrganization upmsOrganization = new UpmsOrganization();
      upmsOrganization.setPid(0);
      List<UpmsOrganization> upmsOrganizations = mapper.selectByCondition(pojo);
      if (upmsOrganizations.size() == 0 || upmsOrganizations.get(0).getId().equals(pojo.getId())) {
        return super.update(pojo);
      } else {
        return ServerResponse.errorMsg("只允许有一个根组织");
      }
    }
    // 检查上级组织是否存在
    UpmsOrganization upmsOrganization = mapper.selectByPrimaryKey(pojo.getPid());
    if (upmsOrganization == null){
      return ServerResponse.errorMsg("上级组织不存在,请重新输入上级组织id");
    } else {
      // 更新组织信息
      return super.update(pojo);
    }
  }

  @Override
  public ServerResponse deleteByIds(List<Integer> integers) {
    // 1. 检查这个id下面还有没有下级组织,如果有就不能删除组织
    UpmsOrganization upmsOrganization = new UpmsOrganization();
    for (Integer integer : integers) {
      if (integer.equals(1)) {
        return ServerResponse.errorMsg("不允许删除根节点");
      }
      upmsOrganization.setPid(integer);
      List<UpmsOrganization> upmsOrganizations = mapper.selectByCondition(upmsOrganization);
      if (upmsOrganizations !=null && upmsOrganizations.size() >0){
        // 该部门下有子节点,不允许删除
        return ServerResponse.errorMsg("组织下尚有子组织,请删除部门下子组织后再进行删除操作");
      }
    }
    //todo  2. 处理组织和用户关联情况
    return super.deleteByIds(integers);
  }

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }

  @Override
  public ServerResponse getOrganizationByUserId(Integer userId) {
    List<UpmsOrganization> upmsOrganizations = mapper.selectJionWithUserId(userId);
    return ServerResponse.successMsgData("根据用户查询组织成功",upmsOrganizations);
  }
}
