//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.app;

import com.atat.core.config.WebDefaultDataConfig;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class WebApplication extends WebDefaultDataConfig {
    public WebApplication() {
    }

//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
//        return factory;
//    }

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
        return new TomcatEmbeddedServletContainerFactory();
    }

    @Bean
    @ConfigurationProperties(
            prefix = "custom.rest.connection"
    )
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate customRestTemplate() {
        return new RestTemplate(this.customHttpRequestFactory());
    }
}
