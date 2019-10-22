package com.micro.fast.common.plugin.lock.optimistic;

import com.micro.fast.common.plugin.MetaObjectKey;
import com.micro.fast.common.plugin.PluginUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mybatis简单的乐观锁插件，不支持级联，不支持批量更新，仅支持单条数据的更新。
 * 使用方式：
 * 1. 给实体类上版本字段加上@Version
 * 2. 给想要使用乐观锁的dao方法上加上@OptimisticLock注解
 * 3. 使用dao方法的时候，更新对象要以实体类的对象为参数。
 * 拦截StatementHandler类的prepare(Connection.class,Integer.class)方法
 * @author lsy
 */
@Intercepts({@Signature(type= StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})})
public class VersionLock implements Interceptor {

    /**
     * 存储已经获取过的statementId 和 fieldNam和字段的反射field
     */
    private static final ConcurrentHashMap<String,CacheOptimisticLock> cache = new ConcurrentHashMap<>();

    /**
     * 需要拦截的方法名
     */
    public static final String PREPARE = "prepare";
    /**
     * sql 中的条件查询符号
     */
    public static final String WHERE = "where";

    /**
     * 拦截器主体方法
     * @param invocation 被拦截的方法的代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String interceptMethodName = invocation.getMethod().getName();
        if (!PREPARE.equals(interceptMethodName)){
            return invocation.proceed();
        }
        // 获取语句对象
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        // MetaObject是mybatis提供的简化插件开发的类
        MetaObject metaObject = MetaObject.forObject(statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory());
        // 获取mapper接口实例，是否指定了乐观锁
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue(MetaObjectKey.DELEGATE_MAPPED_STATEMENT);
        String statementId = mappedStatement.getId();
        // 是否已经获取过这个语句的对应的实体类的版本对象
        StringBuilder fieldName = new StringBuilder();
        BoundSql boundSql = statementHandler.getBoundSql();
        // 获取原始的sql update tableName set a=123,b=234,c=345 where id = 123
        String sql = boundSql.getSql();
        // 获取参数对象实例,检查实体类是不是只有一个参数加了@Version注解
        ParameterHandler parameterHandler = (ParameterHandler)metaObject.getValue(MetaObjectKey.DELEGATE_PARAMETER_HANDLER);
        Object params = parameterHandler.getParameterObject();
        String versionFieldValue = null;
        // 已经获取过，肯定是乐观锁的语句
        if(cache.contains(statementId)){
            CacheOptimisticLock cacheOptimisticLock = cache.get(statementId);
            Field field = cacheOptimisticLock.getField();
            fieldName.append(cacheOptimisticLock.getFieldName());
            field.setAccessible(true);
            versionFieldValue = (String)field.get(params);
            // 修改原本的sql
            sql += " and" + fieldName.toString() + "=" + versionFieldValue;
            int indexOfWhere = sql.indexOf(WHERE);
            String begin = sql.substring(0, indexOfWhere);
            String end = sql.substring(indexOfWhere);
            String optimistVersionSql = begin + " ," + fieldName + "=" + (versionFieldValue+1) + " " + end;
            metaObject.setValue(MetaObjectKey.DELEGATE_BOUND_SQL_SQL,optimistVersionSql);
            return invocation.proceed();
        }else{
            String className = PluginUtils.getClassName(statementId);
            String methodName = PluginUtils.getMethodName(statementId);
            Class<?> aClass = Class.forName(className);
            Method[] methods = aClass.getMethods();
            // 获取实体类的全类名
            String nameDO = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    // 找到mapper接口方法
                    Annotation[] annotations = method.getAnnotations();
                    boolean optimisticLock = false;
                    for (Annotation annotation : annotations) {
                        if (annotation.getClass() == OptimisticLock.class){
                            Parameter[] parameters = method.getParameters();
                            Parameter parameter = parameters[0];
                            nameDO = parameter.getName();
                            optimisticLock = true;
                        }
                    }
                    if (!optimisticLock){
                        return invocation.proceed();
                    }
                    break;
                }
            }
            // 获取参数对象实例,检查实体类是不是只有一个参数加了@Version注解
            Class<?> aClassDO = Class.forName(nameDO);
            Field[] fields = aClassDO.getFields();
            int haveOneVersionFlagfield = 0;
            Field versionField = null;
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() == Version.class){
                        ++ haveOneVersionFlagfield;
                        String name = field.getName();
                        fieldName.append(name);
                        versionField  = field;
                    }
                }
            }
            if (haveOneVersionFlagfield != 1){
                throw new OptimisticException("请保证实体类有且只有一个属性加乐观锁版本注解");
            }
            // 突破属性的访问权限
            versionField.setAccessible(true);
            versionFieldValue = (String)versionField.get(params);
            if (StringUtils.isBlank(versionFieldValue)){
                throw  new OptimisticException("请给版本控制属性赋值");
            }
            // 存储乐观锁
            CacheOptimisticLock cacheOptimisticLock = new CacheOptimisticLock();
            cacheOptimisticLock.setField(versionField);
            cacheOptimisticLock.setFieldName(fieldName.toString());
            cache.put(statementId,cacheOptimisticLock);
            // 修改原本的sql
            sql += " and" + fieldName.toString() + "=" + versionFieldValue;
            int indexOfWhere = sql.indexOf(WHERE);
            String begin = sql.substring(0, indexOfWhere);
            String end = sql.substring(indexOfWhere);
            String optimistVersionSql = begin + " ," + fieldName + "=" + (versionFieldValue+1) + " " + end;
            metaObject.setValue(MetaObjectKey.DELEGATE_BOUND_SQL_SQL,optimistVersionSql);
            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object target) {
        // 封装拦截器为插件,此拦截器只对StatementHandler和ParameterHandler生效，对执行器和ResultSetHandler不生效
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 给插件传递参数
    }
}
