package com.micro.fast.ucenter.service.impl;

import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.ucenter.dao.UcenterUserOauthMapper;
import com.micro.fast.ucenter.pojo.UcenterUserOauth;
import com.micro.fast.ucenter.service.UcenterUserOauthService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lsy
*/
@Service
public class UcenterUserOauthServiceImpl  extends SsmServiceImpl<UcenterUserOauth,Integer,UcenterUserOauthMapper>
implements UcenterUserOauthService<UcenterUserOauth,Integer>,InitializingBean {

  @Autowired
  private UcenterUserOauthMapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }
}
