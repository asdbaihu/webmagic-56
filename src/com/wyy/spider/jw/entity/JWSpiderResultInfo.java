package com.wyy.spider.jw.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 香草航司搜索出来的航班信息JSON对应封装 ,
 * 只封装了其中航班集合FareListOfDay中的
 * Object{
 * 					"FlightDate": "2016-12-01",
                    "LowestFare": 9300,
                    "HighestFare": 13400,
                    "CampaignFlg": false,
                    "OAndDFares": [
                        [
                            {
                                "FltNumber": "105",
                                "FlightDate": "2016-12-01",
                                "Std": "2016-12-01T18:00:00+09:00",
                                "Sta": "2016-12-01T21:15:00+08:00",
                                "Stops": 0,
                                "JourneyTime": "04:15",
                                "LayOverIn": 0,
                                "Flights": [],
                                "Fares": [
                                    {
                                        "FareID": "KKOMII#ADULT#13400.0#1540.0#0.0#INCLUSIVE",
                                        "Seats": 11
                                    },
                                    {
                                        "FareID": "KSMPLI#ADULT#10400.0#1540.0#0.0#SIMPLE",
                                        "Seats": 11
                                    }
                                ],
                                "LowestFare": 10400,
                                "HighestFare": 13400,
                                "CampaignFlg": false,
                                "BoardPoint": "NRT",
                                "TransitPoint": null,
                                "OffPoint": "TPE",
                                "AirlineCode": "JW"
                            }
                        ],
                        [
                            {
                                "FltNumber": "107",
                                "FlightDate": "2016-12-01",
                                "Std": "2016-12-01T22:00:00+09:00",
                                "Sta": "2016-12-02T01:15:00+08:00",
                                "Stops": 0,
                                "JourneyTime": "04:15",
                                "LayOverIn": 0,
                                "Flights": [],
                                "Fares": [
                                    {
                                        "FareID": "VKOMII#ADULT#12300.0#1540.0#0.0#INCLUSIVE",
                                        "Seats": 31
                                    },
                                    {
                                        "FareID": "VSMPLI#ADULT#9300.0#1540.0#0.0#SIMPLE",
                                        "Seats": 31
                                    }
                                ],
                                "LowestFare": 9300,
                                "HighestFare": 12300,
                                "CampaignFlg": false,
                                "BoardPoint": "NRT",
                                "TransitPoint": null,
                                "OffPoint": "TPE",
                                "AirlineCode": "JW"
                            }
                        ]
                    ],
                    "FlightFlg": true
 * }
 * 整条信息：
 * {
    "Status":200,
    "Timestamp":"2016-12-01T17:08:06+09:00",
    "Result":[
        {
            "BoardPoint":"NRT",
            "TransitPoint":null,
            "OffPoint":"TPE",
            "StartDate":"2016-12-01",
            "EndDate":"2016-12-31",
            "LowestFare":6750,
            "HighestFare":32000,
            "CampaignFlg":false,
            "FareListOfDay":{
                "2016-12-01":Object{...},
                "2016-12-02":Object{...},
                "2016-12-03":Object{...},
                "2016-12-04":Object{...},
                "2016-12-05":Object{...},
                "2016-12-06":Object{...},
                "2016-12-07":Object{...},
                "2016-12-08":Object{...},
                "2016-12-09":Object{...},
                "2016-12-10":Object{...},
                "2016-12-11":Object{...},
                "2016-12-12":Object{...},
                "2016-12-13":Object{...},
                "2016-12-14":Object{...},
                "2016-12-15":Object{...},
                "2016-12-16":Object{...},
                "2016-12-17":Object{...},
                "2016-12-18":Object{...},
                "2016-12-19":Object{...},
                "2016-12-20":Object{...},
                "2016-12-21":Object{...},
                "2016-12-22":Object{...},
                "2016-12-23":Object{...},
                "2016-12-24":Object{...},
                "2016-12-25":Object{...},
                "2016-12-26":Object{...},
                "2016-12-27":Object{...},
                "2016-12-28":Object{...},
                "2016-12-29":Object{...},
                "2016-12-30":Object{...},
                "2016-12-31":Object{...}
            },
            "GuestFares":Object{...},
            "FlightFlg":true
        }
    ]
}
 * @author 冯淮港
 *
 */
public class JWSpiderResultInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String FlightDate;//日期 2016-12-01
	private Integer LowestFare;//当天所有报价中最低价(不含税)
	private Integer HighestFare;//当天所有报价中最高价(不含税)
	//一天中所有的航班信息
	/**
	 "OAndDFares":[
				        [
				            Object{...}
				        ],
				        [
				            Object{...}
				        ]
				   ]
	 */
	private List<List<OAndDFare>> OAndDFares = new ArrayList<List<OAndDFare>>();
	private Boolean CampaignFlg;//
	private Boolean FlightFlg;//
	
	public String getFlightDate() {
		return FlightDate.replace("-", "");
	}
	public void setFlightDate(String flightDate) {
		FlightDate = flightDate;
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
	public List<List<OAndDFare>> getOAndDFares() {
		return OAndDFares;
	}
	public void setOAndDFares(List<List<OAndDFare>> oAndDFares) {
		OAndDFares = oAndDFares;
	}
	public Boolean getFlightFlg() {
		return FlightFlg;
	}
	public void setFlightFlg(Boolean flightFlg) {
		FlightFlg = flightFlg;
	}
	@Override
	public String toString() {
		return "JWSpiderResultInfo [FlightDate=" + FlightDate + ", LowestFare="
				+ LowestFare + ", HighestFare=" + HighestFare
				+ ", CampaignFlg=" + CampaignFlg + ", OAndDFares=" + OAndDFares
				+ ", FlightFlg=" + FlightFlg + "]";
	}
	
}
