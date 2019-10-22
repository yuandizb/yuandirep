package com.micro.fast.upms.auto;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 将这个jar中的mybatis的配置自动化
 *
 * @author lsy
 */
public class ThisJarMybatisAutoConfig implements BeanPostProcessor {

    /**
     * 会在bean的初始化方法之前执行 如 {@link org.springframework.beans.factory.InitializingBean} 的afterPropertiesSet方法
     * 执行之前执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 会在bean的初始化方法之后执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof SqlSessionFactory) {
            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) bean;
            Configuration configuration = sqlSessionFactory.getConfiguration();
        }
        return bean;
    }
}
