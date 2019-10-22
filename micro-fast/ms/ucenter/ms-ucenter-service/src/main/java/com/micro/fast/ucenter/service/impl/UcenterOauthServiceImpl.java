package com.micro.fast.ucenter.service.impl;

import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.ucenter.dao.UcenterOauthMapper;
import com.micro.fast.ucenter.pojo.UcenterOauth;
import com.micro.fast.ucenter.service.UcenterOauthService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lsy
*/
@Service
public class UcenterOauthServiceImpl  extends SsmServiceImpl<UcenterOauth,Integer,UcenterOauthMapper>
implements UcenterOauthService<UcenterOauth,Integer>,InitializingBean {

  @Autowired
  private UcenterOauthMapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }
}
