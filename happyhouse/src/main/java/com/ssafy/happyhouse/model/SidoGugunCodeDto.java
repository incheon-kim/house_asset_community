package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SidoGugunCodeDto : 시도, 구군정보", description = "시도, 구군의 이름을 나타낸다.")
public class SidoGugunCodeDto {

	@ApiModelProperty(value = "시도코드")
	private String sidoCode;
	@ApiModelProperty(value = "시도이름")
	private String sidoName;
	@ApiModelProperty(value = "구군코드")
	private String gugunCode;
	@ApiModelProperty(value = "구군이름")
	private String gugunName;
	@ApiModelProperty(value = "읍면동코드")
	private String umdCode;
	@ApiModelProperty(value = "읍면동이름")
	private String umdName;

	public String getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(String sidoCode) {
		this.sidoCode = sidoCode;
	}

	public String getSidoName() {
		return sidoName;
	}

	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}

	public String getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(String gugunCode) {
		this.gugunCode = gugunCode;
	}

	public String getGugunName() {
		return gugunName;
	}

	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}

	public String getUmdCode() {
		return umdCode;
	}

	public void setUmdCode(String umdCode) {
		this.umdCode = umdCode;
	}

	public String getUmdName() {
		return umdName;
	}

	public void setUmdName(String umdName) {
		this.umdName = umdName;
	}
	
}
