package com.micro.fast.ucenter.service.impl;

import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.ucenter.dao.UcenterUserDetailsMapper;
import com.micro.fast.ucenter.pojo.UcenterUserDetails;
import com.micro.fast.ucenter.service.UcenterUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lsy
*/
@Service
public class UcenterUserDetailsServiceImpl  extends SsmServiceImpl<UcenterUserDetails,Integer,UcenterUserDetailsMapper>
implements UcenterUserDetailsService<UcenterUserDetails,Integer>,InitializingBean {

  @Autowired
  private UcenterUserDetailsMapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }
}
