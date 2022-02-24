package com.concordia.message_board.config;

import com.concordia.message_board.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/message").setViewName("viewMessage");
      registry.addViewController("/index.html").setViewName("login");
      registry.addViewController("/postMessage.html").setViewName("postMessage");
      registry.addViewController("/delete.html").setViewName("delete");
    }

     //register interceptor
   @Override
    public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").
           excludePathPatterns("/","/index.html","/authentication", "/webjars/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }


}
