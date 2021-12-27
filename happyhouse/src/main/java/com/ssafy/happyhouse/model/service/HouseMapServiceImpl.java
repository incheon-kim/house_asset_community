package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.TradeDto;
import com.ssafy.happyhouse.model.mapper.HouseMapMapper;
import com.ssafy.happyhouse.util.CrawlerService;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	CrawlerService crawler;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getGugunInSido(sido);
	}

	@Override
	public List<SidoGugunCodeDto> getUmdInGugun(String sido, String gugun) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getUmdInGugun(sido, gugun);
	}
	
	@Override
	public void addTrade(List<TradeDto> trades) throws Exception {
		sqlSession.getMapper(HouseMapMapper.class).addTrade(trades);
	}

	@Override
	public List<TradeDto> getTradeByAddress(String sido, String gugun, String umd, String year, String month, boolean retry) throws Exception {
		List<TradeDto> result = sqlSession.getMapper(HouseMapMapper.class).getTradeByAddress(sido, gugun, umd, year, month);
		String address = this.getAddressFromCode(sido, gugun);
		if (retry && (result == null || result.size() == 0) && address != null) {
			List<TradeDto> crawled = crawler.Crawl(sido+gugun, address, year, month);
			this.addTrade(crawled);
			return getTradeByAddress(sido, gugun, umd, year, month, false);
		}
		return result;
	}

	@Override
	public List<TradeDto> getTradeByName(String name, String year, String month) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getTradeByName(name, year, month);
	}

	@Override
	public String getAddressFromCode(String sido, String gugun) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getAddressFromCode(sido, gugun);
	}
	
	
}
