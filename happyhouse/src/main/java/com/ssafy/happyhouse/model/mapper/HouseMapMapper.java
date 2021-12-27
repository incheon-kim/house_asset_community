package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.TradeDto;

@Mapper
public interface HouseMapMapper {
	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<SidoGugunCodeDto> getUmdInGugun(String sido, String gugun) throws SQLException;
	void addTrade(List<TradeDto> trades) throws Exception;
	List<TradeDto> getTradeByAddress(String sido, String gugun, String umd, String year, String month) throws Exception;
	List<TradeDto> getTradeByName(String name, String year, String month) throws Exception;
	String getAddressFromCode(String sido, String gugun) throws Exception;
}
