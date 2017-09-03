/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.soft;

import com.atat.core.app.WebApplication;
import com.atat.core.config.SpringBeanFactoryUtils;
import com.atat.soft.common.bootitem.MinaServer;
import com.atat.soft.common.prop.SmsPaltformProperty;
import com.atat.soft.common.prop.WxPlatformProperty;
import com.atat.soft.customer.service.impl.TabCustomerServiceImpl;
import com.atat.soft.deviceGroup.service.impl.RelCustomerDeviceGroupServiceImpl;
import com.atat.soft.freshair.service.impl.*;
import com.atat.soft.message.action.WeixinAction;
import com.atat.soft.property.service.impl.TabPropertyMapServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ligw
 * @version $Id ApplicationWeb.java, v 0.1 2017-08-08 23:06 ligw Exp $$
 */
@Configuration
@SpringBootApplication
@EnableScheduling
public class ApplicationWeb extends WebApplication {

    // 需要提前声明的Bean
    //用户信息相关服务
    @Bean(name = "tabCustomerService")
    public TabCustomerServiceImpl getTabCustomerServiceImpl() {
        return new TabCustomerServiceImpl();
    }

    //参数相关服务
    @Bean(name = "tabPropertyMapService")
    public TabPropertyMapServiceImpl getTabPropertyMapServiceImpl() {
        return new TabPropertyMapServiceImpl();
    }

    //短信平台配置
    @Bean(name = "smsPaltformProperty")
    @ConfigurationProperties(prefix = "smsPaltform")
    public SmsPaltformProperty getSmsPaltformProperty(){
        return new SmsPaltformProperty();
    }

    //微信相关配置参数
    @Bean(name = "wxPlatformProperty")
    @ConfigurationProperties(prefix = "wxPlatform")
    public WxPlatformProperty getwxPlatformProperty(){
        return new WxPlatformProperty();
    }

    //空气监测设备相关服务
    @Bean(name = "tabDeviceFreshairService")
    public TabDeviceFreshairServiceImpl getTabDeviceFreshairServiceImpl() {
        return new TabDeviceFreshairServiceImpl();
    }

    //空气检测设备实时数据相关服务
    @Bean(name = "dataFreshairNowService")
    public DataFreshairNowServiceImpl getDataFreshairNowServiceImpl() {
        return new DataFreshairNowServiceImpl();
    }

    //空气检测设备小时节点数据相关服务
    @Bean(name = "dataFreshairHourService")
    public DataFreshairHourServiceImpl getDataFreshairHourServiceImpl() {
        return new DataFreshairHourServiceImpl();
    }

    //空气检测设备天节点数据相关服务
    @Bean(name = "dataFreshairDayService")
    public DataFreshairDayServiceImpl getDataFreshairDayServiceImpl() {
        return new DataFreshairDayServiceImpl();
    }

    //空气检测设备周节点数据相关服务
    @Bean(name = "dataFreshairWeekService")
    public DataFreshairWeekServiceImpl getDataFreshairWeekServiceImpl() {
        return new DataFreshairWeekServiceImpl();
    }

    @Bean(name = "relCustomerDeviceGroupService")
    public RelCustomerDeviceGroupServiceImpl getRelCustomerDeviceGroupServiceImpl(){
        return new RelCustomerDeviceGroupServiceImpl();
    }

    public static void main(final String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ApplicationWeb.class, args);
        SpringBeanFactoryUtils.setApplicationContext(applicationContext);

        //更新accessToken 和jsapiTacket
        WeixinAction.refreshWxaccessToken();
        // freshAir 空气监测
        MinaServer minaServer = new MinaServer();
        minaServer.execute(7777);
    }
}
