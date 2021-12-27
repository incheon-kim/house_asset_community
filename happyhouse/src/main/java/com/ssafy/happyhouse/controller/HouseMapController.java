package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.TradeDto;
import com.ssafy.happyhouse.model.service.HouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/map")
@Api("Map 컨트롤러  API V1")
public class HouseMapController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private HouseMapService houseMapService;
	
	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.info("sido - 호출");
		System.out.println("sido 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(houseMapService.getSido(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "구군 정보", notes = "전국의 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		logger.info("gugun - 호출");
		System.out.println("gugun 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(houseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "읍면동 정보", notes = "시도/구군에 따라 읍면동 반환한다.", response = List.class)
	@GetMapping("/umd")
	public ResponseEntity<List<SidoGugunCodeDto>> umd(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido,
			@RequestParam("gugun") @ApiParam(value = "시군구코드.", required = true) String gugun
			) throws Exception {
		logger.info("umd - 호출");
		System.out.println("umd 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(houseMapService.getUmdInGugun(sido, gugun), HttpStatus.OK);
	}
	
	@GetMapping("/find/address")
	public ResponseEntity<List<TradeDto>> apt(
			@RequestParam("sido") String sido,
			@RequestParam("gugun") String gugun,
			@RequestParam(value="umd", required = false) String umd,
			@RequestParam(value="year") String year,
			@RequestParam(value="month") String month
			) throws Exception {
		return new ResponseEntity<List<TradeDto>>(houseMapService.getTradeByAddress(sido, gugun, umd, year, month, true), HttpStatus.OK);
	}
	
	@GetMapping("/find/name")
	public ResponseEntity<List<TradeDto>> apt(
			@RequestParam("name") String name,
			@RequestParam(value="year") String year,
			@RequestParam(value="month") String month
			) throws Exception {
		return new ResponseEntity<List<TradeDto>>(houseMapService.getTradeByName(name, year, month), HttpStatus.OK);
	}
}
