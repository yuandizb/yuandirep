package com.micro.fast.ucenter.service.impl;

import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.ucenter.dao.UcenterUserMapper;
import com.micro.fast.ucenter.pojo.UcenterUser;
import com.micro.fast.ucenter.service.UcenterUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lsy
*/
@Service
public class UcenterUserServiceImpl  extends SsmServiceImpl<UcenterUser,Integer,UcenterUserMapper>
implements UcenterUserService<UcenterUser,Integer>,InitializingBean {

  @Autowired
  private UcenterUserMapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }
}
