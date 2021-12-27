package com.ssafy.happyhouse.model;

import java.io.Serializable;

public class MemberDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -615477704551759767L;
	
	private String userId;
	private String userName;
	private String userPwd;
	private String email;
	private String address;
	private String phone;
	private int userNo;
	private int role;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "MemberDto [userId=" + userId + ", userName=" + userName + ", email=" + email + ", address=" + address
				+ ", phone=" + phone + ", userNo=" + userNo + ", role=" + role + "]";
	}
}
