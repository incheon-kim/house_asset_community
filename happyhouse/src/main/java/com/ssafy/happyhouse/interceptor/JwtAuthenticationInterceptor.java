package com.ssafy.happyhouse.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import com.ssafy.happyhouse.exception.UnAuthorizedException;
import com.ssafy.happyhouse.util.JwtProvider;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
	
	@Autowired
	JwtProvider jwtManager;
	
	@Override
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object Handler) throws Exception {
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}
		Cookie tokenCookie = WebUtils.getCookie(request, "jwt-token");
		
		if (tokenCookie != null && jwtManager.isUsable(tokenCookie.getValue())) {
			return true;
		}
		throw new UnAuthorizedException();
	}
}
