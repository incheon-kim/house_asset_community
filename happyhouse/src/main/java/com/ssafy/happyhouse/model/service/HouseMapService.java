package com.ssafy.happyhouse.model.service;

import java.util.List;

//import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.TradeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<SidoGugunCodeDto> getUmdInGugun(String sido, String gugun) throws Exception;
	void addTrade(List<TradeDto> trades) throws Exception;
	List<TradeDto> getTradeByAddress(String sido, String gugun, String umd, String year, String month, boolean retry) throws Exception;
	List<TradeDto> getTradeByName(String name, String year, String month) throws Exception;
	String getAddressFromCode(String sido, String gugun) throws Exception;
}
