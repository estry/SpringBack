package com.ssafy.happyhouse.model.mapper;

import com.ssafy.happyhouse.model.MemberDto;

import java.sql.SQLException;

public interface MemberMapper {

//	MemberDto login(Map<String, String> map) throws Exception;
	
	int idCheck(String checkId) throws Exception;
	void registerMember(MemberDto memberDto) throws Exception;
	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	int deleteMember(String userId) throws SQLException;
	
//	List<MemberDto> listMember() throws Exception;
//	MemberDto getMember(String userId) throws Exception;
	int updateMember(MemberDto memberDto) throws Exception;
//	MemberDto getMemberInterest(String userId) throws SQLException;
}
