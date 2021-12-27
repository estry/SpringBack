package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.HouseDealDto;

import java.util.List;
import java.util.Map;

public interface HouseDealService {
	List<HouseDealDto> getHouseDealList(String dong) throws Exception;
	List<HouseDealDto> getHouseDealAptList(Map<String, String> map) throws Exception;
}
