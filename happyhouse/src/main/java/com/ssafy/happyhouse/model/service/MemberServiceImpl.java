package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public MemberDto login(String id, String pwd) throws SQLException {
		return sqlSession.getMapper(MemberMapper.class).login(id, pwd);
	}

	@Override
	public int register(Map<String, String> member) throws SQLException {
		return sqlSession.getMapper(MemberMapper.class).register(member);
	}

	@Override
	public int modify(MemberDto member) throws SQLException {
		return sqlSession.getMapper(MemberMapper.class).modify(member);
	}

	@Override
	public int idCheck(String id) throws SQLException {
		return sqlSession.getMapper(MemberMapper.class).idCheck(id);
	}

	@Override
	public void quit(MemberDto member) throws Exception {
		sqlSession.getMapper(MemberMapper.class).quit(member);
	}
	
	@Override
	public MemberDto verify(Map<String, String> claims) throws SQLException {
		System.out.println(claims.toString());
		return sqlSession.getMapper(MemberMapper.class).verify(claims);
	}

	@Override
	public int getRole(String userId) throws SQLException {
		return sqlSession.getMapper(MemberMapper.class).getRole(userId);
	}

	@Override
	public int checkEmail(String id, String email) throws SQLException {
		return sqlSession.getMapper(MemberMapper.class).checkEmail(id, email);
	}
	
	@Override
	public void updatePwd(String id, String newPwd) throws SQLException {
		sqlSession.getMapper(MemberMapper.class).updatePwd(id, newPwd);
	}
}
