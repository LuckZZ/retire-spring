package com.example.comm.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Create by : Zhangxuemeng
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${file.pictures.res.url}")
    private String filePicturesResUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LoggerFactory.getLogger(WebAppConfig.class).info("imagesPath="+ filePicturesResUrl);
        registry.addResourceHandler("/images/**").addResourceLocations(filePicturesResUrl);
        super.addResourceHandlers(registry);
    }

}
