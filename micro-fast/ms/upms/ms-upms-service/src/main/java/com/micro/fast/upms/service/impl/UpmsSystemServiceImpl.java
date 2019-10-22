package com.micro.fast.upms.service.impl;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.service.impl.SsmServiceImpl;
import com.micro.fast.upms.dao.UpmsSystemMapper;
import com.micro.fast.upms.pojo.UpmsSystem;
import com.micro.fast.upms.service.UpmsSystemService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lsy
*/
@Service
public class UpmsSystemServiceImpl  extends SsmServiceImpl<UpmsSystem,Integer,UpmsSystemMapper>
implements UpmsSystemService<UpmsSystem,Integer>,InitializingBean {

  @Autowired
  private UpmsSystemMapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }

  @Override
  public ServerResponse getSystemStatus(Integer id) {
    int systemStatus = mapper.selectSystemStatus(id);
    return ServerResponse.successMsgData("获取系统状态成功",systemStatus);
  }
}
