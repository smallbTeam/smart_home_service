/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.core.db.service;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * druid监控
 * @author ligw
 * @version $Id DruidStatViewServlet.java, v 0.1 2017-08-08 21:59 ligw Exp $$
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/druid/*",
        initParams={
                @WebInitParam(name="allow",value=""),// IP白名单 (没有配置或者为空，则允许所有访问)
                @WebInitParam(name="deny",value="192.168.1.111"),// IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name="loginUsername",value="admin"),// 用户名
                @WebInitParam(name="loginPassword",value="admin"),// 密码
                @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
        })
public class DruidStatViewServlet extends StatViewServlet{

}
