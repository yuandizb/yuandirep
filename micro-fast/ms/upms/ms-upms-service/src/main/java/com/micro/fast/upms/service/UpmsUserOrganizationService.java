package com.micro.fast.upms.service;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.SsmService;

import java.util.List;

/**
* @author lsy
*/
public interface UpmsUserOrganizationService{
  /**
   * 批量建立用户和组织的关系
   * @param userId
   * @param orgIds
   * @return
   */
  ServerResponse batchInsert(Integer userId, List<Integer> orgIds);

  /**
   * 批量删除用户和组织的关系
   * @param userId
   * @param orgIds
   * @return
   */
  ServerResponse batchDelete(Integer userId, List<Integer> orgIds);
}