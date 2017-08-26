//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.atat.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WebDefaultDataConfig extends DefaultDatabaseConfiguration {

    @Value("${swagger.basePath}")
    private String basePath;

    public WebDefaultDataConfig(){
    }

    @Bean
    public Docket createRestApi() {
        return (new Docket(DocumentationType.SWAGGER_2)).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage(this.basePath)).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title("spring_mybatis 提供的API信息").description("API信息有疑问请联系相关开发人员").termsOfServiceUrl("").contact("lgw").version("1.0").build();
    }

}
