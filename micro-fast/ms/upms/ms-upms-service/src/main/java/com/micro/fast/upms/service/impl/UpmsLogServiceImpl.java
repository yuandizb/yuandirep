package com.micro.fast.upms.service.impl;

import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.upms.dao.UpmsLogMapper;
import com.micro.fast.upms.pojo.UpmsLog;
import com.micro.fast.upms.service.UpmsLogService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lsy
*/
@Service
public class UpmsLogServiceImpl  extends SsmServiceImpl<UpmsLog,Integer,UpmsLogMapper>
implements UpmsLogService<UpmsLog,Integer>,InitializingBean {

  @Autowired
  private UpmsLogMapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }
}
