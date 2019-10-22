package com.micro.fast.upms.service.impl;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.upms.dao.UpmsUserOrganizationMapper;
import com.micro.fast.upms.service.UpmsUserOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lsy
*/
@Service
public class UpmsUserOrganizationServiceImpl  implements UpmsUserOrganizationService {

  @Autowired
  private UpmsUserOrganizationMapper mapper;

  @Override
  public ServerResponse batchInsert(Integer userId, List orgIds) {
    int i = mapper.batchInsertOneUserWithManyOrg(userId, orgIds);
    if (i>0) {
      return ServerResponse.successMsg("加入组织成功");
    } else {
      return ServerResponse.errorMsg("加入组织失败");
    }
  }

  @Override
  public ServerResponse batchDelete(Integer userId, List orgIds) {
    int i = mapper.batchDeleteByUserIdOrganizationId(userId, orgIds);
    if (i>0) {
      return ServerResponse.successMsg("退出组织成功");
    } else {
      return ServerResponse.errorMsg("退出组织失败");
    }
  }
}
