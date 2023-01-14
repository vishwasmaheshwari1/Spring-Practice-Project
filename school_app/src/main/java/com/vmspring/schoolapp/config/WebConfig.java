package com.vmspring.schoolapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    //if only want to parse view without inclusion of model (i.e. without business logic)
    //then it's better to use WebMvcConfigurer registry
    //instead of creating a separate Controller

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //WebMvcConfigurer.super.addViewControllers(registry);

        registry.addViewController("/courses").setViewName("courses");
        registry.addViewController("/about").setViewName("about");

    }
}
