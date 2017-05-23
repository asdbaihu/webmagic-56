package com.wyy.spider.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wyy.spider.common.utils.DateUtil;
import com.wyy.spider.common.utils.GlobalSetting;
import com.wyy.spider.jw.biz.JWSpiderBiz;

public class BootAppJW {

	private static final Logger log =  LoggerFactory.getLogger(BootAppJW.class);
	// 接口服务类
	private JWSpiderBiz jwSpiderBiz ;
	
	private static String configFile1 	= GlobalSetting.getConfigFile1() ;
	private static String configFile2 	= GlobalSetting.getConfigFile2() ;
	
	
	/**
	 * 航线组
	 * @throws InterruptedException 
	 */
	public void spiderAirLine( String[] args ) throws InterruptedException{  
		// 取爬取航线组号 ，默认为01组
		String lineNum = "01" ;
		if( args != null && args.length>0 ) 
			lineNum = args[0] ;
		
		log.debug( " ************************** 爬取航线组号: " + lineNum + " ************************ " ) ;
		
		// 取爬取航线
		String airLine 	= GlobalSetting.getSpiderAirLine( lineNum ) ;
		
		// 
		String[]  alArr = airLine.split(",");
		/**
		 * 爬当月及之后的两个月
		 */
		String [] spiderDateArr = DateUtil.getCurrentMonthAfter(2);
		
		String from = "" ;
		String to   = "" ;
		
		int spdLength 	= spiderDateArr.length ;
		int alLength	= alArr.length ;
		String[]  urls  = new String[]{};
		StringBuilder urlBuilder = new StringBuilder();
		for( int i=0; i<spdLength; i++ ) {	
			for(int j=0; j<alLength; j++ ) {	
				from 	= alArr[j].substring(0,3) ;
				to   	= alArr[j].substring(3) ;
//						 .append("https://www.vanilla-air.com/api/booking/flight-fare/list.json?__ts="+System.currentTimeMillis()+"&adultCount=1&childCount=0&couponCode=&currency=JPY&destination="+to+"&infantCount=0&isMultiFlight=true&origin="+from+"&targetMonth="+spiderDateArr[i]+"&version=1.0");
				urlBuilder.append(",")
						 .append("https://www.vanilla-air.com/api/booking/flight-fare/list.json?__ts="+System.currentTimeMillis())
						 .append("&adultCount=1&childCount=0&couponCode=")
						 .append("&infantCount=0&isMultiFlight=true")
						 .append("&version=1.0")
						 .append("&origin="+from)//出发地
						 .append("&destination="+to)//目的地
						 .append("&targetMonth="+spiderDateArr[i])//时间  航司官网一次搜索整个月的对应航班信息 YYYYMM
						 .append("&currency=JPY");//币种
				;
				// 记录日志
				log.debug(" spiderAirLine :-> from = " + from + ", to = " + to + ", date = " + spiderDateArr[i] );
			}
		}
		 urls = urlBuilder.toString().substring(1).split(",");
		 jwSpiderBiz.searchAirLine(urls);
	}
	
	private static void startApp( String[] args ) throws InterruptedException {
		ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext(new String[] { configFile1, configFile2 } ) ; 
				
		BootAppJW boot = (BootAppJW) factory.getBean("bootAppJW");
		boot.spiderAirLine( args ); 
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		long start 	= System.currentTimeMillis() ;
//		startApp( args );
		startApp( new String[]{"15"} );
		long end 	= System.currentTimeMillis() ;
		
		log.debug( " ************************** 总共用时 : " +  (start-end) + " ************************** " ) ;
		System.exit(0);
	}

	 

	public void setJwSpiderBiz(JWSpiderBiz jwSpiderBiz) {
		this.jwSpiderBiz = jwSpiderBiz;
	}

	public static void setConfigFile1(String configFile1) {
		BootAppJW.configFile1 = configFile1;
	}

	public static void setConfigFile2(String configFile2) {
		BootAppJW.configFile2 = configFile2;
	}
 

}
