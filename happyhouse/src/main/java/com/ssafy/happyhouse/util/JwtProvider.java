package com.ssafy.happyhouse.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.service.MemberService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	
	@Autowired
	MemberService memberService;
	
	private final SecretKey key;
	private final Long TOKEN_EXPIRE_TIME = 1000 * 60L * 60L * 2L; // 2 hours
	
	public JwtProvider(@Value("${jwt.secret}") String secret) {
		System.out.println(secret);
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		System.out.println(Encoders.BASE64.encode(this.key.getEncoded()));
	}
	
	public String createToken(MemberDto member, HttpServletResponse response) {
		// JWT HEADER
		Claims headers = Jwts.claims();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");
		
		// JWT payload
		Claims payloads = Jwts.claims();
		System.out.println(member.toString());
		payloads.put("userId", member.getUserId());
		payloads.put("name", member.getUserName());
		payloads.put("id", Integer.toString(member.getUserNo()));
		payloads.put("role", member.getRole());
		
		Date ext = new Date();
		ext.setTime(ext.getTime() + TOKEN_EXPIRE_TIME);
		
		String jwt = Jwts.builder()
				.setHeader(headers)
				.setIssuer("happyhouse")
				.setSubject("user")
				.setClaims(payloads)
				.setExpiration(ext)
				.signWith(this.key)
				.compact();
		System.out.println(jwt);
		
		Cookie token = new Cookie("jwt-token", jwt);
		token.setPath("/");
		token.setMaxAge(TOKEN_EXPIRE_TIME.intValue() / 1000);
		token.setHttpOnly(true);
		
		response.addCookie(token);
		
		return jwt;
	}
	
	public boolean isUsable(String jwt) throws Exception {
		return validateToken(jwt);
	}
	
	public void invalidateToken(Cookie token, HttpServletResponse response) {
		token.setMaxAge(0);
		token.setPath("/");
		response.addCookie(token);
	}
	
	public Map<String, String> verifyToken(String jwt) throws Exception {
		if (validateToken(jwt))
			return parseToken(jwt);
		else
			return null;
	}
	
	private Map<String, String> parseToken(String jwt) throws Exception {
		Claims body = Jwts.parserBuilder()
						.setSigningKey(this.key)
						.build()
						.parseClaimsJws(jwt)
						.getBody();
		return this.claimToMap(body);
	}
	
	private boolean validateToken(String jwt) throws Exception{
		try {
			Jwts.parserBuilder()
				.setSigningKey(this.key)
				.build()
				.parseClaimsJws(jwt);
			return true;
		} catch (Exception e) {
			System.out.println("Given Token is not proper token...");
			return false;
		}
	}
	
	private Map<String, String> claimToMap(Claims claims){
		Map<String, String> expected = new HashMap<String, String>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expected.put(entry.getKey(), entry.getValue().toString());
		}
		return expected;
	}
}
