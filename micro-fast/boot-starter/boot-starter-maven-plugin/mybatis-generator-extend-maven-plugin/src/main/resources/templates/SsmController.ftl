package ${mybatisGenerator.controllerPackage};

import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import ${mybatisGenerator.pojoPackage}.${pojoName};
import ${mybatisGenerator.servicePackage}.${pojoName}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
* @author lsy
*/
@Api("${varPojoName}")
@RestController
@RequestMapping("/${varPojoName}")
public class ${pojoName}Controller {

  @Autowired
  private ${pojoName}Service<${pojoName},${mybatisGenerator.primaryKeyType}> ${varPojoName}Service;

  @ApiOperation("添加信息")
  @PostMapping
  public ServerResponse add${pojoName}(@Valid ${pojoName} ${varPojoName}) throws BindException {
    return  ${varPojoName}Service.add(${varPojoName});
  }

  @ApiOperation("根据id查询详细信息")
  @GetMapping
  public ServerResponse get${pojoName}ById(
  @RequestParam("id") @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    return ${varPojoName}Service.getById(${mybatisGenerator.primaryKeyType}.valueOf(id));
  }

  @ApiOperation("根据条件分页查询")
  @GetMapping("/byPageCondition")
  public ServerResponse get${pojoName}ByCondition(${pojoName} ${varPojoName},
                                             @RequestParam(defaultValue = "1",required = false) int pageNum,
                                             @RequestParam(defaultValue = "10",required = false)int pageSize,
                                             @RequestParam(required = false) String orderBy){
    return ${varPojoName}Service.getByCondition(${varPojoName},pageNum,pageSize,orderBy);
  }

  @ApiOperation("修改信息")
  @PutMapping
  public ServerResponse update${pojoName}(@Valid ${pojoName} ${varPojoName},
                                     @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请传入id") String id){
    ${varPojoName}.setId(${mybatisGenerator.primaryKeyType}.valueOf(id));
    return ${varPojoName}Service.update(${varPojoName});
  }

  @ApiOperation("根据id删除，传入数组")
  @DeleteMapping
  public ServerResponse delete${pojoName}(@NotEmpty(message = BaseConst.BASEMSG_PREFIX+"请传入ids") @RequestParam("ids") ArrayList<${mybatisGenerator.primaryKeyType}> ids){
    return ${varPojoName}Service.deleteByIds(ids);
  }
}
