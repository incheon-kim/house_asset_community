package com.ssafy.happyhouse.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.happyhouse.exception.UnAuthorizedException;

@RestControllerAdvice
public class AuthenticationAdvice {
	@ExceptionHandler(UnAuthorizedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public String description() {
		return "Access Token is not valid";
	}
}
