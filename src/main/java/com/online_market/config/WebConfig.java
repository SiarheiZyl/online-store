package com.online_market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;


/**
 * Class to boot the application
 *
 * @author Siarhei
 * @version 1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.online_market"})
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Method register multipartResolver bean in spring context.
     *
     * @return CommonsMultipartResolver. It used in application for uploading images
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(1000000);
        return resolver;
    }
}
