package com.ssafy.happyhouse.model.mapper;

import com.ssafy.happyhouse.model.HouseDealDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface HouseDealMapper {
	List<HouseDealDto> getHouseDealList(String dong) throws SQLException;
	List<HouseDealDto> getHouseDealAptList(Map<String, String> map) throws SQLException;
}
