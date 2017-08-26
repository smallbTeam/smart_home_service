/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.core.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Spring容器工具类
 * @author ligw
 * @version $Id SpringBeanFactoryUtils.java, v 0.1 2017-08-09 14:24 ligw Exp $$
 */
@Configuration
public class SpringBeanFactoryUtils {

    private static ApplicationContext applicationContext;

    public static void  setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(null == SpringBeanFactoryUtils.applicationContext) {
            SpringBeanFactoryUtils.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    //通过name获取 Bean
    public static Object getBean( String beanName ) {
        return getApplicationContext().getBean( beanName );
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T>T getBean(String beanName , Class<T>clazz) {
        return getApplicationContext().getBean(beanName , clazz);
    }
}
