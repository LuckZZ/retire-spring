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

    @Value("${file.pictures.url}")
    private String filePicturesUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
 /*       if(filePicturesUrl.equals("") || filePicturesUrl.equals("/usr/local/staticfile/pictures/")){
            String imagesPath = WebAppConfig.class.getClassLoader().getResource("").getPath();
            if(imagesPath.indexOf(".jar")>0){
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
            }else if(imagesPath.indexOf("classes")>0){
                imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf("classes"));
            }
            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/images/";
            filePicturesUrl = imagesPath;
        }*/
        LoggerFactory.getLogger(WebAppConfig.class).info("imagesPath="+ filePicturesUrl);
        registry.addResourceHandler("/images/**").addResourceLocations("file:D:/"+filePicturesUrl);
        super.addResourceHandlers(registry);
    }

}
