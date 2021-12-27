package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.CategoryDto;
import com.ssafy.happyhouse.model.StoreInfoDto;
import com.ssafy.happyhouse.model.mapper.StoreInfoMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StoreInfoServiceImpl implements StoreInfoService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<StoreInfoDto> getStoreInfo(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(StoreInfoMapper.class).getStoreInfo(map);
	}

	@Override
	public List<StoreInfoDto> getIntStoreInfo(String dong) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(StoreInfoMapper.class).getIntStoreInfo(dong);
	}

	@Override
	public List<CategoryDto> getLargeCategory() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(StoreInfoMapper.class).getLargeCategory();
	}

	@Override
	public List<CategoryDto> getMediumCategory(String largeCategory) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(StoreInfoMapper.class).getMediumCategory(largeCategory);
	}

	@Override
	public List<CategoryDto> getSmallCategory(String smallCategory) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(StoreInfoMapper.class).getSmallCategory(smallCategory);
	}

	@Override
	public List<StoreInfoDto> getSearchStoreInfo(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(StoreInfoMapper.class).getSearchStoreInfo(map);
	}

}
