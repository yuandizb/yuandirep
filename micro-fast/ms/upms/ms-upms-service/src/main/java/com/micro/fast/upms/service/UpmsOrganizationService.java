package com.micro.fast.upms.service;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.SsmService;

/**
* @author lsy
*/
public interface UpmsOrganizationService<T,ID> extends SsmService<T,ID>{
  ServerResponse getOrganizationByUserId(Integer userId);
}