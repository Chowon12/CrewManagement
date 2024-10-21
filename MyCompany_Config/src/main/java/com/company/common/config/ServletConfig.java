package com.company.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.company.common.interceptor.SessionInterceptor;

@Configuration
// spring MVC패턴을 가능하게 함. 13번째 줄
@EnableWebMvc
// 24번째 줄
@ComponentScan(basePackages = {"com.company.controller"})
public class ServletConfig implements WebMvcConfigurer {
	
	// void addResourceHandlers(ResourceHandlerRegistry registry)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");
	}
	
	// void configureViewResolvers(ViewResolverRegistry registry)
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		//
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	//위 두가지는 필수임. 
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		return multipartResolver;
		// 26~30줄 용량이 크지 않으니 굳이 설정해줄 필요 없고 인코딩부분만 정해줌.
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionInterceptor())
				.addPathPatterns("/dept/**")
				.addPathPatterns("/modify/**")
				.addPathPatterns("/logout")
				.excludePathPatterns("/login")
				.excludePathPatterns("/main");
	}
}
