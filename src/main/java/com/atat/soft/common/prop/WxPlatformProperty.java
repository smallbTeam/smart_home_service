package com.atat.soft.common.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ligw
 * @version $Id BasePropertyDate.java, v 0.1 2017-06-24 21:31 ligw Exp $$
 */
@Component("wxPlatformProperty")
@ConfigurationProperties(prefix = "wxPlatform")
public class WxPlatformProperty {
    public String wxAppId;
    public String wxSecret;
    public String wxWarnModel;///微信警告模板

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getWxSecret() {
        return wxSecret;
    }

    public void setWxSecret(String wxSecret) {
        this.wxSecret = wxSecret;
    }

    public String getWxWarnModel() {
        return wxWarnModel;
    }

    public void setWxWarnModel(String wxWarnModel) {
        this.wxWarnModel = wxWarnModel;
    }
}
