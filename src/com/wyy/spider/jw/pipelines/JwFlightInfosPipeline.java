package com.wyy.spider.jw.pipelines;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ruizn.base.utils.StringUtil;
import com.wyy.spider.common.entity.CreepFlight;
import com.wyy.spider.common.utils.FastJsonUtil;
import com.wyy.spider.common.utils.RedisUtil;
import com.wyy.spider.jw.entity.Fare;
import com.wyy.spider.jw.entity.JWSpiderResultInfo;
import com.wyy.spider.jw.entity.OAndDFare;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
/**
 * 搜索航班信息完成 后的后置处理实现
 * @author 冯淮港
 *
 */
public class JwFlightInfosPipeline implements Pipeline {
    
	private static final Logger log     	= Logger.getLogger(JwFlightInfosPipeline.class);
	private static final Integer dbIndex 	= 2;
	
	private RedisUtil redisUtil;
	
	private String expireTime;//爬取数据过期时间设定
	
	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	// ResultItems保存了抽取结果，它是一个Map结构，
    // 在page.putField(key,value)中保存的数据，可以通过ResultItems.get(key)获取
	@Override
	public void process(ResultItems result, Task task) {
		 Object o = result.get("res");
		 if(StringUtil.isNotBlank(o)){
			 //这个result是个json
			 Map<String, Object> map2 = FastJsonUtil.toMap(o.toString());
			 Integer status = Integer.parseInt(map2.get("Status").toString());
			 if(status==200){
				 //搜索正常
				 Object object = map2.get("Result");
				 List<Object> list = FastJsonUtil.toList(object.toString(),Object.class);
				 //往redis库中存放相关数据，定义map容器
				 Map<String,String> redisInfos  = null;
				 String from 					= null;
				 String ret 					= null;
				 
				 for (Object o2 : list) {//[{}]其实就一个
					 Map<String, Object> map = FastJsonUtil.toMap(o2.toString());
					 Object res3 = map.get("FareListOfDay");//获取整个月的所有航班信息
					 Map<String, Object> map3 = FastJsonUtil.toMap(res3.toString());
					 Set<String> set = map3.keySet();
					 for (String date : set) {//遍历每一天的详细情况 
						 Map<String, Object> map4 = FastJsonUtil.toMap(map3.get(date).toString());
						 /**
						  *   "OAndDFares":[
							        Array[1],
							        [
							            Object{...}
							        ],
							        Array[1]
							    ]
						  */
						 JWSpiderResultInfo jwSpiderResultInfo = FastJsonUtil.json2bean(map4.toString(), JWSpiderResultInfo.class);
						 //一天中的所有航班信息
						 List<List<OAndDFare>> oAndDfares = jwSpiderResultInfo.getOAndDFares();
						 //存储信息实体封装
						 CreepFlight creepFlight = null;
						 String cabin			 = null;
						 redisInfos   = new HashMap<String, String>();
						 
						 for (List<OAndDFare> oAndDFare : oAndDfares) {//某天中的所有航班信息遍历
							 creepFlight 	= new CreepFlight();
							 //包含单价及税费等相关信息的数据
							 /**
							  *   "FareID":"OKOMI#ADULT#7940.0#380.0#0.0#INCLUSIVE",
                        	  *	  "Seats":24
							  */
							 for (OAndDFare oneFare : oAndDFare) {
								 List<Fare> fares = oneFare.getFares();
								 //座位数
								 Integer seats     = null;
								 for (Fare fare : fares) {
									 //获取廉价舱价格参数
									 /** 搜索请求要有 1 个成人 1 个儿童
									  * "Fares": [
								  {
				                    "FareID": "RKOMI#ADULT#17890.0#380.0#0.0#INCLUSIVE",
				                    "Seats": 38
				                  },
				                  {
				                    "FareID": "RKOMI#CHILD#17890.0#190.0#0.0#INCLUSIVE",
				                    "Seats": 38
				                  },
				                  {
				                    "FareID": "RSMPL#ADULT#15890.0#380.0#0.0#SIMPLE",
				                    "Seats": 38
				                  },
				                  {
				                    "FareID": "RSMPL#CHILD#15890.0#190.0#0.0#SIMPLE",
				                    "Seats": 38
				                  }
								]
									  */
									 //确定廉价舱成人报价参数
									 if(fare.getFareID().contains("ADULT")&&fare.getFareID().contains("SIMPLE")){
										 String fareID = fare.getFareID();
										 String[] split = fareID.split("#");
										 //座位数
										 seats  		= fare.getSeats();
										 if(split.length>5){
											 if(StringUtil.isNotBlank(split[0])){
												 cabin = split[0].substring(0, 1);
											 }
											 if(StringUtil.isNotBlank(split[2])){
												 //含税成人价
												 creepFlight.setAdultPrice(Double.parseDouble(split[2])+Double.parseDouble(split[3]) + 600);
											 }
											 if(StringUtil.isNotBlank(split[3])){
												 //成人税费 +服务费600jpy
												 creepFlight.setAdultTax(Double.parseDouble(split[3]) + 600);
											 }
											 
										 }
									 }
									 //确定廉价舱儿童报价参数
//							 if(fare.getFareID().contains("CHILD")&&fare.getFareID().contains("SIMPLE")){
//								 String fareID = fare.getFareID();
//								 String[] split = fareID.split("#");
//								 if(split.length>5){
//									 if(StringUtil.isNotBlank(split[2])){
//										 //不含税成人价
//										 creepFlight.setChildPrice(Double.parseDouble(split[2]));
//									 }
//									 if(StringUtil.isNotBlank(split[3])){
//										 //成人税费+服务费600jpy
//										 creepFlight.setChildTax(Double.parseDouble(split[3]) + 600);
//									 }
//									 
//								 }
//							 }
									 
									 
								 }
								 creepFlight.setAdultTaxType(0);//0不含税
								 creepFlight.setChildPrice(0D);
								 creepFlight.setChildTax(0D);
								 creepFlight.setChildTaxType(0);
								 creepFlight.setCurrency("JPY");
								 creepFlight.setArrAirport(oneFare.getOffPoint());
								 ret				= oneFare.getOffPoint();
								 /**
								  * "Std":"2017-01-01T13:10:00+09:00",
								  * "Sta":"2017-01-01T15:10:00+09:00"
								  */
								 creepFlight.setArrTime(oneFare.getSta());
								 creepFlight.setCabin(cabin);
								 creepFlight.setCabinClass(null);
								 creepFlight.setCarrier(oneFare.getAirlineCode());
								 creepFlight.setCfDate(new Date());
								 creepFlight.setDepAirport(oneFare.getBoardPoint());
								 from 					= oneFare.getBoardPoint();
								 creepFlight.setDepTime(oneFare.getStd());
								 creepFlight.setFlightNumber(oneFare.getAirlineCode()+oneFare.getFltNumber());
								 creepFlight.setFromDate(date.replace("-", ""));
								 creepFlight.setMaxSeats(seats);
								 creepFlight.setRetDate("");
								 creepFlight.setStopCities(oneFare.getTransitPoint());
								 creepFlight.setTripType("1");
								 creepFlight.setOriginal_Data(map4.toString());
								 redisInfos.put(creepFlight.getFlightNumber()+"~"+creepFlight.getDepTime()+"~"+creepFlight.getArrTime(), FastJsonUtil.bean2json(creepFlight));
							 }
							 
						 }
						 long dateofneed = 5*60*60*1000;//初始设置5小时
						 if(StringUtil.isNotBlank(expireTime)){//判断配置文件中的设置过期时间是否存在
							 dateofneed = Integer.parseInt(expireTime)*60*60*1000;
						 }
						 String hashKey = "JW_"+date.replace("-", "")+"_"+from+"_"+ret;
						 //先删旧值
						 redisUtil.delete(hashKey, dbIndex);
						 //后加新值
						 redisUtil.setAllHash(hashKey, redisInfos,dateofneed, dbIndex);
						 log.debug("JW_"+date.replace("-", "")+"_"+from+"_"+ret);
					 }
				 }
			 }else{
				 log.debug("请求响应错误，参数码:"+status);
			 }
		 }
	}
	 
}
