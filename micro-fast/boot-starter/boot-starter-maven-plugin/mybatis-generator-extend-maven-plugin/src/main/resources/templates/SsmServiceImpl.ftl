package ${mybatisGenerator.serviceImplPackage};

import com.micro.fast.common.service.impl.SsmServiceImpl;
import ${mybatisGenerator.daoPackage}.${pojoName}Mapper;
import ${mybatisGenerator.pojoPackage}.${pojoName};
import ${mybatisGenerator.servicePackage}.${pojoName}Service;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lsy
*/
@Service
public class ${pojoName}ServiceImpl  extends SsmServiceImpl<${pojoName},${mybatisGenerator.primaryKeyType},${pojoName}Mapper>
implements ${pojoName}Service<${pojoName},${mybatisGenerator.primaryKeyType}>,InitializingBean {

  @Autowired
  private ${pojoName}Mapper mapper;

  /**
  * 在这个bean初始化完成后覆盖父类的mapper
  */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.setMapper(this.mapper);
  }
}
