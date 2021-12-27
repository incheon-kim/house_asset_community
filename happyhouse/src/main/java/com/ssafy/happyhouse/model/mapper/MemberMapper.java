package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.MemberDto;

@Mapper
public interface MemberMapper {
	MemberDto login(String id, String pwd) throws SQLException;
	int register(Map<String, String> member) throws SQLException;
	int modify(MemberDto member) throws SQLException;
	int idCheck(String id) throws SQLException;
	int checkEmail(String id, String email) throws SQLException;
	void quit(MemberDto member) throws Exception;
	MemberDto verify(Map<String, String> claims);
	int getRole(String userId) throws SQLException;
	void updatePwd(String id, String newPwd) throws SQLException;
}
