package com.atat.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 *
 *
 * @version : 1.0
 * @description: 跨域配置类
 * @author: ligangwei
 * @company rainsunset
 * @date: 2020 -03-16
 * @CreateDate: 2019 -05-27
 */
@Configuration
public class CorsConfiguration {

    @Value("${allow.origin}")
    /* 环境域名 */
    private String allowedOriginsStr;

    /**
     * 让配置类能够使用@Value注解
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //解析域名列表
                if (allowedOriginsStr != null) {
                    String[] allowedOrigins = allowedOriginsStr.split(",");

                    /**
                     * 全局配置
                     * 匹配路径中 /** 表示该路径下的所有路径, 如果只有一个* ,只代表其子路径, 其他的都包括
                     */
                    registry.addMapping("/**")
                            .allowCredentials(true)
                            .allowedMethods("POST", "GET", "OPTIONS")
                            .allowedOrigins(allowedOrigins)
                            .maxAge(3600);
                }
            }

            @Override
            public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

            }

            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

            }

            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

            }

            @Override
            public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

            }

            @Override
            public void addFormatters(FormatterRegistry formatterRegistry) {

            }

            @Override
            public void addInterceptors(InterceptorRegistry interceptorRegistry) {

            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

            }


            @Override
            public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

            }

            @Override
            public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

            }

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

            }

            @Override
            public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

            }

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> list) {

            }

            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

            }

            @Override
            public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

            }

            @Override
            public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

            }

            @Override
            public Validator getValidator() {
                return null;
            }

            @Override
            public MessageCodesResolver getMessageCodesResolver() {
                return null;
            }
        };
    }
}
