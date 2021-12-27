package com.ssafy.happyhouse.model.mapper;

import com.ssafy.happyhouse.model.CategoryDto;
import com.ssafy.happyhouse.model.StoreInfoDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface StoreInfoMapper {
	
	List<CategoryDto> getLargeCategory() throws SQLException;
	List<CategoryDto> getMediumCategory(String largeCategory) throws SQLException;
	List<CategoryDto> getSmallCategory(String smallCategory) throws SQLException;
	List<StoreInfoDto> getStoreInfo(Map<String, String> map) throws SQLException;
	List<StoreInfoDto> getIntStoreInfo(String dong) throws SQLException;
	List<StoreInfoDto> getSearchStoreInfo(Map<String, String> map) throws SQLException;
}
