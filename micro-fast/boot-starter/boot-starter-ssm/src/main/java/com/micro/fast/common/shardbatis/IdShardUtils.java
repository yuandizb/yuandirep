package com.micro.fast.common.shardbatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据idshard注解判断哪个参数是分表的依据，以及根据反射获取分表的依据参数的值
 *
 * @author lsy
 */
@Slf4j
public class IdShardUtils {

  public static final String PARAM = "param";

  /**
   * 工具类私有构造防止被实例化
   */
  private IdShardUtils() {

  }

  /**
   * 根据statementId反射出方法，查找方法中带有IdShard朱姐标记的参数，获取其值用于分表策略
   *
   * @param params
   * @param statementId
   * @return
   */
  public static Object getShardIdValue(Object params, String statementId) throws Exception {
    int lastIndexOfD = statementId.lastIndexOf(".");
    String methodName = statementId.substring(lastIndexOfD + 1);
    String className = statementId.substring(0, lastIndexOfD);
    Class<?> aClass = null;
    try {
      aClass = Class.forName(className);
      Method[] methods = aClass.getMethods();
      for (Method method : methods) {
        String name = method.getName();
        int parameterIndex = 0;
        // 方法名相同就找到了语句
        if (name.equals(methodName)) {
          Parameter[] parameters = method.getParameters();
          for (Parameter parameter : parameters) {
            parameterIndex++;
            // 参数是否包含IdShard.class注解
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
              // 找到带有分表标记符号的参数
              if (annotation.annotationType() == IdShard.class) {
                Class<?> type = parameter.getType();
                // statement是不是只有一个参数，有一个的参数的话参数类型应该相同.但是这一个参数不能是map类型
                if (params.getClass() == type) {
                    return getValueByFieldName((IdShard) annotation,params);
                } else {
                  // 如果有多个参数的话，mybatis会把参数封装成map类型,key是param+参数的顺序
                  String key  = PARAM + (parameterIndex + 1);
                  Map<String,Object> map = (HashMap<String,Object>)params;
                  Object o = map.get(key);
                  return getValueByFieldName((IdShard) annotation,o);
                }
              }
            }

          }
        }
      }
    } catch (ClassNotFoundException e) {
      log.info("sql语句对应的类不存在:{}", e);
    }
    // 找不到就返回null
    return null;
  }

  /**
   * 根据注解和mybatis的参数
   * @param idShard
   * @param params
   * @return
   * @throws Exception
   */
  private static Object getValueByFieldName(IdShard idShard, Object params) throws Exception {
    // 如果是基础类型（表明是主键）就直接返回
    if (isBasicType(params)) {
      return params;
    } else {
      // 如果不是基础类型，就使用IdShard的value属性指定对象的哪个属性是分表策略需要的值
      String shardFieldName = idShard.value();
      if (StringUtils.isBlank(shardFieldName)) {
        throw new Exception("the shardFieldName was not annotated");
      }
      Field field = null;
      try {
        // 根据字段名反射获取字段的类类型
        field = params.getClass().getDeclaredField(shardFieldName);
      } catch (NoSuchFieldException e) {
        field = params.getClass().getSuperclass().getDeclaredField(shardFieldName);
      }
      // 设置属性的访问权限
      field.setAccessible(true);
      // 获取对象中属性的值
      return field.get(params);
    }
  }

  /**
   * 　检查参数是不是基础类型
   *
   * @param param
   * @return
   */
  private static boolean isBasicType(Object param) {
    if (param == null) {
      return false;
    }
    if (param instanceof String) {
      return true;
    }
    if (param instanceof BigDecimal) {
      return true;
    }
    if (param instanceof Integer) {
      return true;
    }
    if (param instanceof Long) {
      return true;
    }
    if (param instanceof Double) {
      return true;
    }
    if (param instanceof Float) {
      return true;
    }
    if (param instanceof Character) {
      return true;
    }
    if (param instanceof Byte) {
      return true;
    }
    if (param instanceof Short) {
      return true;
    }
    if (param instanceof Boolean) {
      return true;
    }
    return false;
  }


}
