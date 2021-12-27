package com.ssafy.happyhouse.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.happyhouse.interceptor.JwtAuthenticationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	private static final String[] RESOURCE_PATHS = {
			"classpath:/static/"
	};
	
	private static final String[] JWT_INCLUDE_PATHS = {
			"/map/**", "/api/user/logout", "/api/user/modify", "/api/user/quit", "/board/write"
	};
	
	@Autowired
	JwtAuthenticationInterceptor jwtInterceptor;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
////		registry.addMapping("/**")
////				.allowedOrigins("http://localhost:8080")
////				.allowedMethods("*")
////				.allowedHeaders("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers")
////				.allowCredentials(true)
////				.maxAge(6000);
		registry.addMapping("/**").allowedOrigins("http://localhost:8080", "http://localhost:9999")
				.allowedMethods("*")
				.allowedHeaders("*")
				//.allowCredentials(true)
				.maxAge(6000);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations(RESOURCE_PATHS);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns(Arrays.asList(JWT_INCLUDE_PATHS));
	}
	
}
