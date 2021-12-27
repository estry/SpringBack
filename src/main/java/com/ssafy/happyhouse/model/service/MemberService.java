package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.MemberDto;


public interface MemberService {

//	MemberDto login(Map<String, String> map) throws Exception;
	
	int idCheck(String checkId) throws Exception;
	void registerMember(MemberDto memberDto) throws Exception;
	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	boolean deleteMember(String userId) throws Exception;
	
//	List<MemberDto> listMember() throws Exception;
//	MemberDto getMember(String userId) throws Exception;
	boolean updateMember(MemberDto memberDto) throws Exception;
//	MemberDto getMemberInterest(String userId) throws Exception;

}
