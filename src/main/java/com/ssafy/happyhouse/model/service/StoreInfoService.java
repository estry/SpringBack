package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.CategoryDto;
import com.ssafy.happyhouse.model.StoreInfoDto;

import java.util.List;
import java.util.Map;

public interface StoreInfoService {
	List<CategoryDto> getLargeCategory() throws Exception;
	List<CategoryDto> getMediumCategory(String largeCategory) throws Exception;
	List<CategoryDto> getSmallCategory(String smallCategory) throws Exception;
	List<StoreInfoDto> getStoreInfo(Map<String, String> map) throws Exception;
	List<StoreInfoDto> getIntStoreInfo(String dong) throws Exception;
	List<StoreInfoDto> getSearchStoreInfo(Map<String, String> map) throws Exception;
}
