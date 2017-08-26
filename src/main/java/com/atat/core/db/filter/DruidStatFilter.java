/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.core.db.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;
/**
 * druid拦截器
 *
 * @author ligw
 * @version $Id DruidStatFilter.java, v 0.1 2017-08-08 22:05 ligw Exp $$
 */
@WebFilter(
        filterName = "druidWebStatFilter",
        urlPatterns = "/*",
        initParams = {@WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
        })
public class DruidStatFilter extends WebStatFilter {
}
