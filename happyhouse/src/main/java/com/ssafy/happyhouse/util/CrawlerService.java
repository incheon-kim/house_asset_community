package com.ssafy.happyhouse.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.happyhouse.model.TradeDto;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDate;

@SuppressWarnings("unused")
@Service
public class CrawlerService {
	int nullCount = 0;
	boolean flag = true;
	
	private String getTagValue(String tag, Element element) {
	      NodeList nList = element.getElementsByTagName(tag).item(0).getChildNodes();
	      Node node = (Node)nList.item(0);
	      if(node == null) return null;
	      return node.getTextContent().trim();
	}
	
	public List<TradeDto> Crawl(String mainCode, String address, String year, String month) throws Exception {
	    String url = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
		// openAPI(data.go.kr) serviceKey
	    String servicekey = "";
	    String LAWD_CD = mainCode;
	    String DEAL_YMD = year + ((month.length() == 1) ? "0": "") + month;
	    
	    StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(servicekey, "UTF-8"));
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	    urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("150", "UTF-8"));
	    urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(LAWD_CD, "UTF-8"));
	    urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(DEAL_YMD, "UTF-8"));
	    Document document = DocumentBuilderFactory
	            .newInstance()
	            .newDocumentBuilder()
	            .parse(urlBuilder.toString());
	  
	    document.getDocumentElement().normalize();
	    NodeList nList = document.getElementsByTagName("item");
	    //System.out.println("Crawled data count -> " + nList.getLength());
	    
	    if (nList.getLength() > 0) {
	    		nullCount = 0;
				List<TradeDto> result = new ArrayList<>();
				for (int i = 0; i < nList.getLength(); i++) {
					Node node = nList.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						try {
							Element element = (Element)node;
							TradeDto trade = new TradeDto();
							trade.setAptCode(getTagValue("일련번호", element));
							trade.setPrice(getTagValue("거래금액", element).replaceAll(",", ""));
							trade.setConstructYear(getTagValue("건축년도", element));
							
							// trade date
							LocalDate date = LocalDate.of(Integer.parseInt(getTagValue("년", element)), 
											Integer.parseInt(getTagValue("월", element)), 
											Integer.parseInt(getTagValue("일", element)));
							trade.setTradeDate(Date.valueOf(date));
							trade.setDong(getTagValue("법정동", element));
							trade.setBonbun(getTagValue("법정동본번코드", element));
							trade.setBubun(getTagValue("법정동부번코드", element));
							trade.setMainCode(getTagValue("법정동시군구코드", element));
							trade.setSubCode(getTagValue("법정동읍면동코드", element));
							trade.setApt(getTagValue("아파트", element));
							
							trade.setArea(getTagValue("전용면적", element));
							trade.setFloor(getTagValue("층", element));
							
							trade = getCoordinate(address, trade);
							result.add(trade);
						} catch (NullPointerException e) {
							nullCount++;
						}
					}
		        }
		        return result;
	    }
	    return null;
	}
	
	@SuppressWarnings("unchecked")
	private TradeDto getCoordinate(String address, TradeDto trade) {
		// build query string
		StringBuilder query = new StringBuilder(address).append(" ")
														.append(trade.getDong()).append(" ")
														.append(trade.getApt());
		try {
			// set http url with query string
			URL naverURL = new URL("https://new.land.naver.com/api/search?keyword=" 
									+ URLEncoder.encode(query.toString(), "UTF-8"));
			ObjectMapper mapper = new ObjectMapper();
			// get response (JSON)
			Map<String, Object>response = mapper.readValue(naverURL, Map.class);
			// if there is no result throw Exception
			if ("false".equals(response.get("isShown")))
				throw new IOException();
			// get Complexes array (they hold information)
			ArrayList<Map<String, Object>> complexes = (ArrayList<Map<String, Object>>) response.get("complexes");
			// get first complex information
			Map<String, Object> complex = complexes.get(0);
			
			// lat, rng geocode
			String lat = complex.get("latitude").toString();
			String rng = complex.get("longitude").toString();
			String deepLink = complex.get("deepLink").toString();
			
			trade.setLat(lat);
			trade.setRng(rng);
			trade.setDeepLink("https://new.land.naver.com" + deepLink);
			
		} catch (Exception e) {
			// naver land api failed. need to call kakao api
			System.out.println("Naver crawl failed");
			trade.setDeepLink("https://new.land.naver.com");
		}
		return trade;
	}
}
