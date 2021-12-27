package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.mapper.HouseDealMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HouseDealServiceImpl implements HouseDealService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<HouseDealDto> getHouseDealList(String dong) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(HouseDealMapper.class).getHouseDealList(dong);
	}

	@Override
	public List<HouseDealDto> getHouseDealAptList(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(HouseDealMapper.class).getHouseDealAptList(map);
	}

}
