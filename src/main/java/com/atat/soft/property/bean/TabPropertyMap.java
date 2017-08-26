package com.atat.soft.property.bean;

import java.util.Date;
/**
 * 配置表
 * @author wuhaosoft
 * @version $Id TabPropertyMap.java, 2017-08-10 01:19:52 wuhaosoft Exp
 *
 */
public class TabPropertyMap {

    //配置 - 属性
    private String  tabPropertyMapId;

    //propval
    private String propval;

    public TabPropertyMap(){}

    public TabPropertyMap(
        String  tabPropertyMapId,
        String propval
    ) {
        this.propval = propval;
    }

    public String  getTabPropertyMapId(){
        return tabPropertyMapId;
    }

    public void setTabPropertyMapId(String  tabPropertyMapId) {
        this.tabPropertyMapId = tabPropertyMapId;
    }


    public String getPropval() {
        return propval;
    }

    public void setPropval(String  propval) {
        this.propval = propval;
    }

}



