package com.ssafy.happyhouse.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.happyhouse.exception.UnAuthorizedException;
import com.ssafy.happyhouse.model.LoginDto;
import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.service.MemberService;
import com.ssafy.happyhouse.util.JwtProvider;

@RequestMapping("/api/user")
@RestController
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String FAIL = "failed";
	private static final String SUCCESS = "success";
	
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	JwtProvider jwtManager;
	
	@PostMapping("/info")
	public ResponseEntity<MemberDto> getInformation(@CookieValue(name = "jwt-token", required = false) Cookie token) throws Exception {
		logger.info("user - 정보 요청");
		System.out.println("user - 정보 요청");
		if (token != null) {
			Map<String, String> userInformation = jwtManager.verifyToken(token.getValue());
			MemberDto user = memberService.verify(userInformation);
			if (user != null) {
				return new ResponseEntity<MemberDto>(user, HttpStatus.OK);
			}
		}
		throw new UnAuthorizedException();
	}
	
	@PostMapping("/login")
	public ResponseEntity<MemberDto> login(
			@RequestBody LoginDto member, HttpServletResponse response) throws Exception
	{	
		logger.info("user - login 시도");
		System.out.println(member);
		MemberDto user = memberService.login(member.getUserId(), member.getUserPwd());
		if (user != null) {
			String token = jwtManager.createToken(user, response);
			return new ResponseEntity<MemberDto>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<MemberDto>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Boolean> logout(
			@CookieValue(name = "jwt-token", required = false) Cookie token,
			HttpServletResponse response) throws Exception {
		jwtManager.invalidateToken(token, response);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@PostMapping("/join")
	public ResponseEntity<Boolean> join(@RequestBody Map<String, String> member) throws SQLException {
		System.out.println(member);
		boolean result = false;
		if (memberService.register(member) > 0) {
			result = true;
		}
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping("/modify")
	public ResponseEntity<String> modify(
			@RequestBody Map<String, String> params,
			@CookieValue(name = "jwt-token", required = false) Cookie token
			) throws Exception {
		
		MemberDto member = new MemberDto();
		member.setUserNo(params.get("userNo").charAt(0)-'0');
		member.setUserName(params.get("userName"));
		member.setUserId(params.get("userId"));
		member.setUserPwd(params.get("userPwd"));
		member.setEmail(params.get("email"));
		member.setPhone(params.get("phone"));
		member.setAddress(params.get("address"));
		System.out.println(member);
		MemberDto realMember = memberService.login(member.getUserId(), member.getUserPwd());
		
		Map<String, String> tokenValues = jwtManager.verifyToken(token.getValue());
		if (realMember != null &&
				tokenValues.get("userId").equals(member.getUserId())) {
			member.setUserPwd(params.get("newPwd"));
			member.setUserNo(realMember.getUserNo());
			if (memberService.modify(member) > 0)
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/idcheck")
	public ResponseEntity<Boolean> idCheck(@RequestParam("userId") String userId) throws SQLException {
		boolean result = false;
		if (memberService.idCheck(userId) > 0) {
			result = true;
		}
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping("/quit")
	public ResponseEntity<String> quit(@CookieValue(name = "jwt-token", required = false) Cookie token, HttpServletResponse response) throws Exception {
		Map<String, String> userInformation = jwtManager.verifyToken(token.getValue());
		MemberDto user = memberService.verify(userInformation);
		if (user != null)
			memberService.quit(user);
		jwtManager.invalidateToken(token, response);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@PutMapping("/checkEmail")
	public ResponseEntity<Boolean> checkEmail(@RequestBody MemberDto memberDto) throws SQLException {
		boolean result = false;
		if (memberService.checkEmail(memberDto.getUserId(), memberDto.getEmail()) > 0) {
			result = true;
		}
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PutMapping("/getNewPwd")
	public ResponseEntity<String> getNewPwd(@RequestBody MemberDto memberDto) throws SQLException {
		System.out.println("getNewPwd - 호출");
		
		// 임시 비밀번호 생성
		String newPwd = "";
		for (int i = 0; i < 12; i++) {
			newPwd += (char) ((Math.random() * 26) + 97);
		}
		memberService.updatePwd(memberDto.getUserId(), newPwd);
		return new ResponseEntity<String>(newPwd, HttpStatus.OK);
//		boolean result = false;
//		if (memberService.checkEmail(memberDto.getUserId(), memberDto.getEmail()) > 0) {
//			result = true;
//		}
//		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@GetMapping("/role")
	public ResponseEntity<Integer> getRole(@CookieValue(name = "jwt-token", required = false) Cookie token) throws Exception {
		if (token == null) {
			return new ResponseEntity<Integer>(0, HttpStatus.UNAUTHORIZED);
		}
		Map<String, String> userInformation = jwtManager.verifyToken(token.getValue());
		return new ResponseEntity<Integer>(Integer.parseInt(userInformation.get("role")), HttpStatus.OK);
	}
}
