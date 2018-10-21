package com.online_market.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {

        return new Class[] {SpringConfig.class, WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {

        return new String[] {"/"};
    }
}