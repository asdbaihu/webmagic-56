package com.wyy.spider.jw.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OAndDFare implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fltNumber;// 航班号
	private String flightDate;// 飞行日期 2016-12-01
	private String Std;// 出发时间2016-12-01T18:00:00+09:00
	private String Sta;// 到达时间2016-12-01T21:15:00+08:00
	private Integer Stops;// 经停？？？
	private String JourneyTime;//飞行时长
	private Integer LayOverIn;
	private List<Flight> Flights = new ArrayList<Flight>();
	private List<Fare> Fares = new ArrayList<Fare>();
	private Integer LowestFare;//最低成人价
	private Integer HighestFare;//最高成人价
	private Boolean CampaignFlg;//
	private String BoardPoint;//出发地
	private String TransitPoint;//中转
	private String OffPoint;//目的地
	private String AirlineCode;//航司二字码
	public String getFltNumber() {
		return fltNumber;
	}

	public void setFltNumber(String fltNumber) {
		this.fltNumber = fltNumber;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getStd() {
		String a = null;
		a		 = Std.replace("-", "").replace("T", "").replace(":", "");
		//201701011510   00+0900
		return a.substring(0, 12);
	}

	public void setStd(String std) {
		Std = std;
	}
	/**
	  * "Std":"2017-01-01T13:10:00+09:00",
	  * "Sta":"2017-01-01T15:10:00+09:00"
	  
	 */
	public String getSta() {
		String a = null;
		a		 = Sta.replace("-", "").replace("T", "").replace(":", "");
		//201701011510   00+0900
		return a.substring(0, 12);
	}

	public String getFromDate() {
		String a = null;
		a		 = Std.replace("-", "").split("T")[0];
		//201701011510   00+0900
		return a;
	}
	
	public void setSta(String sta) {
		Sta = sta;
	}

	public Integer getStops() {
		return Stops;
	}

	public void setStops(Integer stops) {
		Stops = stops;
	}

	public String getJourneyTime() {
		return JourneyTime;
	}

	public void setJourneyTime(String journeyTime) {
		JourneyTime = journeyTime;
	}

	public Integer getLayOverIn() {
		return LayOverIn;
	}

	public void setLayOverIn(Integer layOverIn) {
		LayOverIn = layOverIn;
	}

	public List<Flight> getFlights() {
		return Flights;
	}

	public void setFlights(List<Flight> flights) {
		Flights = flights;
	}

	public List<Fare> getFares() {
		return Fares;
	}

	public void setFares(List<Fare> fares) {
		Fares = fares;
	}

	public Integer getLowestFare() {
		return LowestFare;
	}

	public void setLowestFare(Integer lowestFare) {
		LowestFare = lowestFare;
	}

	public Integer getHighestFare() {
		return HighestFare;
	}

	public void setHighestFare(Integer highestFare) {
		HighestFare = highestFare;
	}

	public Boolean getCampaignFlg() {
		return CampaignFlg;
	}

	public void setCampaignFlg(Boolean campaignFlg) {
		CampaignFlg = campaignFlg;
	}

	public String getBoardPoint() {
		return BoardPoint;
	}

	public void setBoardPoint(String boardPoint) {
		BoardPoint = boardPoint;
	}

	public String getTransitPoint() {
		return TransitPoint;
	}

	public void setTransitPoint(String transitPoint) {
		TransitPoint = transitPoint;
	}

	public String getOffPoint() {
		return OffPoint;
	}

	public void setOffPoint(String offPoint) {
		OffPoint = offPoint;
	}

	public String getAirlineCode() {
		return AirlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		AirlineCode = airlineCode;
	}



	class Flight {

	}
}
