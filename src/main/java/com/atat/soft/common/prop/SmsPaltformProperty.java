/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.soft.common.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ligw
 * @version $Id SMSPaltformProperty.java, v 0.1 2017-08-10 17:08 ligw Exp $$
 */
@Component("smsPaltformProperty")
@ConfigurationProperties(prefix = "smsPaltform")
public class SmsPaltformProperty {
    private String name;
    private String pwd;
    private String  id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
