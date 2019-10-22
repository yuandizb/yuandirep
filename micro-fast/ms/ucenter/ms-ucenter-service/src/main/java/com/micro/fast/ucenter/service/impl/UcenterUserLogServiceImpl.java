package com.micro.fast.ucenter.service.impl;

import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.ucenter.dao.UcenterUserLogMapper;
import com.micro.fast.ucenter.pojo.UcenterUserLog;
import com.micro.fast.ucenter.service.UcenterUserLogService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lsy
*/
@Service
public class UcenterUserLogServiceImpl  extends SsmServiceImpl<UcenterUserLog,Integer,UcenterUserLogMapper>
implements UcenterUserLogService<UcenterUserLog,Integer>,InitializingBean {

  @Autowired
  private UcenterUserLogMapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }
}
