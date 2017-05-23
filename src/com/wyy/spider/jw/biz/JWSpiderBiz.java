package com.wyy.spider.jw.biz;



public interface JWSpiderBiz {
 

	/**
	 * 爬取当天到当前月底的对应航线所有航班信息
	 *  
	 */
	abstract void searchAirLine(String[] urls)  ;
}
