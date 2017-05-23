package com.wyy.spider.jw.entity;

import java.io.Serializable;

/**
 * 包含税费、座位数信息
 *                            成人价          税费                手续费
 *     "FareID":"VKOMII#ADULT#12300.0#1540.0#0.0#INCLUSIVE",
       "Seats":31
 * 
 * 
 * @author 冯淮港
 *
 */
public class Fare implements Serializable {

	private static final long serialVersionUID = 1L;

	private String FareID;
	private Integer Seats;

	public String getFareID() {
		return FareID;
	}
	public void setFareID(String fareID) {
		FareID = fareID;
	}
	public Integer getSeats() {
		return Seats;
	}
	public void setSeats(Integer seats) {
		Seats = seats;
	}
}
