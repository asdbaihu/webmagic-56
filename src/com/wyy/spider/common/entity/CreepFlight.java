package com.wyy.spider.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.wyy.spider.common.utils.UUIDGenerator;


/**
 * 航班信息bean
 * 
 * @author lzh
 *
 */
 
public class CreepFlight implements Serializable{

	private static final long serialVersionUID = 1L;
	// 爬取UUID
	private String uuid = UUIDGenerator.getUUID();
	// 航司编码
	private String carrier;
	// 航班号
	private String flightNumber;
	// 行程类型
	private String tripType;
	// 出发地
	private String depAirport;
	// 目的地
	private String arrAirport;
	// 出发日期
	private String fromDate;
	// 回程日期
	private String retDate;
	// 起飞时间
	private String depTime;
	// 到达时间
	private String arrTime;
	// 经停地
	private String stopCities;
	// 舱位
	private String cabin; 
	// 舱位等级
	private Integer cabinClass;
	// 舱位数量
	private Integer maxSeats;
	// 货币币种
	private String currency;
	// 成人单价
	private Double adultPrice;
	// 成人税费
	private Double adultTax;
	// 儿童单价
	private Double childPrice;
	// 儿童税费
	private Double childTax;
	// 成人税费类型
	private Integer adultTaxType;
	// 儿童税费类型
	private Integer childTaxType;
	// 爬取日期
	private Date cfDate;
	// 原始jsonData
	private String original_Data;
	
	//无参构造方法
	public CreepFlight() {
		super();
	}
	
	// 带参构造方法
	public CreepFlight(String cabin, Double adultPrice, Double adultTax,
			Integer maxSeats, Date cfDate) {
		super();
		this.cabin = cabin;
		this.adultPrice = adultPrice;
		this.adultTax = adultTax;
		this.maxSeats = maxSeats;
		this.cfDate = cfDate;
	}

	// 带参构造方法
	public CreepFlight(String uuid, String carrier, String flightNumber,
			String tripType, String depAirport, String arrAirport,
			String fromDate, String retDate, String depTime, String arrTime,
			String stopCities, String cabin, Integer cabinClass,
			Integer maxSeats, String currency, Double adultPrice,
			Double adultTax, Double childPrice, Double childTax,
			Integer adultTaxType, Integer childTaxType, Date cfDate,
			String original_Data) {
		super();
		this.uuid = uuid;
		this.carrier = carrier;
		this.flightNumber = flightNumber;
		this.tripType = tripType;
		this.depAirport = depAirport;
		this.arrAirport = arrAirport;
		this.fromDate = fromDate;
		this.retDate = retDate;
		this.depTime = depTime;
		this.arrTime = arrTime;
		this.stopCities = stopCities;
		this.cabin = cabin;
		this.cabinClass = cabinClass;
		this.maxSeats = maxSeats;
		this.currency = currency;
		this.adultPrice = adultPrice;
		this.adultTax = adultTax;
		this.childPrice = childPrice;
		this.childTax = childTax;
		this.adultTaxType = adultTaxType;
		this.childTaxType = childTaxType;
		this.cfDate = cfDate;
		this.original_Data = original_Data;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getDepAirport() {
		return depAirport;
	}

	public void setDepAirport(String depAirport) {
		this.depAirport = depAirport;
	}

	public String getArrAirport() {
		return arrAirport;
	}

	public void setArrAirport(String arrAirport) {
		this.arrAirport = arrAirport;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getRetDate() {
		return retDate;
	}

	public void setRetDate(String retDate) {
		this.retDate = retDate;
	}

	public String getDepTime() {
		return depTime;
	}

	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}

	public String getArrTime() {
		return arrTime;
	}

	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}

	public String getStopCities() {
		return stopCities;
	}

	public void setStopCities(String stopCities) {
		this.stopCities = stopCities;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public Integer getCabinClass() {
		return cabinClass;
	}

	public void setCabinClass(Integer cabinClass) {
		this.cabinClass = cabinClass;
	}


	public Integer getMaxSeats() {
		return maxSeats;
	}

	public void setMaxSeats(Integer maxSeats) {
		this.maxSeats = maxSeats;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public Double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Double getAdultTax() {
		return adultTax;
	}
	public void setAdultTax(Double adultTax) {
		this.adultTax = adultTax;
	}
	
	public Double getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}
	public Double getChildTax() {
		return childTax;
	}
	public void setChildTax(Double childTax) {
		this.childTax = childTax;
	}
	public Integer getAdultTaxType() {
		return adultTaxType;
	}

	public void setAdultTaxType(Integer adultTaxType) {
		this.adultTaxType = adultTaxType;
	}

	public Integer getChildTaxType() {
		return childTaxType;
	}

	public void setChildTaxType(Integer childTaxType) {
		this.childTaxType = childTaxType;
	}

	public Date getCfDate() {
		return cfDate;
	}

	
	public void setCfDate(Date cfDate) {
		this.cfDate = cfDate;
	}

	public String getOriginal_Data() {
		return original_Data;
	}

	public void setOriginal_Data(String original_Data) {
		this.original_Data = original_Data;
	}


}
