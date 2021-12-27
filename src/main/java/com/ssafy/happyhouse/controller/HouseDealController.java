package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.service.HouseDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deal")
@CrossOrigin("*")
public class HouseDealController {

	@Autowired
	private HouseDealService houseDealService;

	@GetMapping("deallist")
	ResponseEntity<List<HouseDealDto>> getHouseDealList(@RequestParam("dong") String dong) throws Exception {
		return new ResponseEntity<List<HouseDealDto>>(houseDealService.getHouseDealList(dong), HttpStatus.OK);
	}

	@GetMapping("dealapt")
	ResponseEntity<List<HouseDealDto>> getHouseAptDealList(@RequestParam Map<String, String> map) throws Exception {
		return new ResponseEntity<List<HouseDealDto>>(houseDealService.getHouseDealAptList(map), HttpStatus.OK);
	}
}
