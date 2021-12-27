package com.ssafy.happyhouse.model;

public class MemberDto {
	private String userid;
	private String userName;
	private String userpwd;
	private String email;
	private String joinDate;
	private int state;
	private String interest;

	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getUserId() {
		return userid;
	}
	public void setUserId(String userId) {
		this.userid = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userpwd;
	}
	public void setUserPwd(String userPwd) {
		this.userpwd = userPwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "MemberDto [userId=" + userid + ", userName=" + userName + ", userPwd=" + userpwd + ", email=" + email
				+ ", joinDate=" + joinDate + ", state=" + state + ", interest=" + interest + "]";
	}


}
