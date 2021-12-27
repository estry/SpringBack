package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int idCheck(String checkId) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).idCheck(checkId); // 0 or 1
	}

	@Override
	public void registerMember(MemberDto memberDto) throws Exception {
//		validation check
		sqlSession.getMapper(MemberMapper.class).registerMember(memberDto);
	}

	@Override
	public boolean deleteMember(String userId) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).deleteMember(userId) == 1;
	}

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if(memberDto.getUserId() == null || memberDto.getUserPwd() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}
	
	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
	}
	
	//	@Override
//	public MemberDto login(Map<String, String> map) throws Exception {
//		return sqlSession.getMapper(MemberMapper.class).login(map);
//	}
//	
//	@Override
//	public List<MemberDto> listMember() throws Exception {
//		return sqlSession.getMapper(MemberMapper.class).listMember();
//	}
//
//	@Override
//	public MemberDto getMember(String userId) throws Exception {
//		return sqlSession.getMapper(MemberMapper.class).getMember(userId);
//	}
//
	@Override
	public boolean updateMember(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).updateMember(memberDto)==1;
	}
//
//
//	@Override
//	public MemberDto getMemberInterest(String userId) throws Exception {
//		// TODO Auto-generated method stub
//		return sqlSession.getMapper(MemberMapper.class).getMemberInterest(userId);
//	}
}
