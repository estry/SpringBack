package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.model.CategoryDto;
import com.ssafy.happyhouse.model.StoreInfoDto;
import com.ssafy.happyhouse.model.service.StoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/store")
public class StoreInfoController {
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@GetMapping("large")
	ResponseEntity<List<CategoryDto>> getLargeCategory() throws Exception {
		return new ResponseEntity<List<CategoryDto>>(storeInfoService.getLargeCategory(), HttpStatus.OK);
	}
	
	@GetMapping("medium")
	ResponseEntity<List<CategoryDto>> getMediumCategory(@RequestParam String largeCategory) throws Exception {
		return new ResponseEntity<List<CategoryDto>>(storeInfoService.getMediumCategory(largeCategory), HttpStatus.OK);
	}
	
	@GetMapping("small")
	ResponseEntity<List<CategoryDto>> getSmallCategory(String mediumCategory) throws Exception {
		return new ResponseEntity<List<CategoryDto>>(storeInfoService.getSmallCategory(mediumCategory), HttpStatus.OK);
	}
	
	@GetMapping("stores")
	ResponseEntity<List<StoreInfoDto>> getStoreInfo(@RequestParam Map<String, String> map) throws Exception {
		return new ResponseEntity<List<StoreInfoDto>>(storeInfoService.getStoreInfo(map), HttpStatus.OK);
	}
	
	@GetMapping("storelist")
	ResponseEntity<List<StoreInfoDto>> getIntStoreInfo(String dong) throws Exception {
		return new ResponseEntity<List<StoreInfoDto>>(storeInfoService.getIntStoreInfo(dong), HttpStatus.OK);
	}
	
	@GetMapping("search")
	ResponseEntity<List<StoreInfoDto>> getSearchStoreInfo(@RequestParam Map<String, String> map) throws Exception {		
		return new ResponseEntity<List<StoreInfoDto>>(storeInfoService.getSearchStoreInfo(map),HttpStatus.OK);
	}
}
