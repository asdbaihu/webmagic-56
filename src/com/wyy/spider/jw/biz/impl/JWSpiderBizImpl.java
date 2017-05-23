package com.wyy.spider.jw.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.JMException;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

import com.wyy.spider.common.utils.FastJsonUtil;
import com.wyy.spider.common.utils.JedisUtil;
import com.wyy.spider.common.utils.RedisUtil;
import com.wyy.spider.common.utils.SpringContextUtil;
import com.wyy.spider.jw.biz.JWSpiderBiz;
import com.wyy.spider.jw.pipelines.JwFlightInfosPipeline;


public class JWSpiderBizImpl implements JWSpiderBiz,PageProcessor {
   
	private static final Logger log = Logger.getLogger(JWSpiderBizImpl.class); 
	  

	private JwFlightInfosPipeline jwFlightInfosPipeline;
	
	private static RedisUtil redisUtil = (RedisUtil) SpringContextUtil.getBean("redisUtil");
	
	private Site site =Site.me().addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0")
			.addHeader("Accept", "application/json, text/plain, */*")
			.addHeader("Host", "www.vanilla-air.com")
			.addHeader("Referer", "https://www.vanilla-air.com/en/booking/")
			.addHeader("channel", "pc")
			.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
			.addHeader("Accept-Encoding", "gzip, deflate, br")
			.setRetryTimes(3)//重试次数
			.setSleepTime(1000); //重试间隔

	public void setJwFlightInfosPipeline(JwFlightInfosPipeline jwFlightInfosPipeline) {
		this.jwFlightInfosPipeline = jwFlightInfosPipeline;
	}
	
	/**
	 * 爬虫自由定制业务实现
	 */
	@Override
	public void process(Page page) {
		Json json = page.getJson();
		page.putField("res", json.toString());
	}

	@Override
	public Site getSite() {
		site.setHttpProxyPool(httpProxyList());
		return site;
	}
	@Override
	public void searchAirLine(String[] urls) {
		Spider jwSpider = Spider.create(new JWSpiderBizImpl()).addUrl(urls)
				.thread(42)//14条航线*爬取3个月的数据量
				.addPipeline(jwFlightInfosPipeline);//后置处理		
//				 try {
//					SpiderMonitor.instance().register(jwSpider);//注册爬虫监控
//				} catch (JMException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				 jwSpider.run();//启动，阻塞当前线程
	}
	
	public List<String[]> httpProxyList(){
		List<String[]> list	= new ArrayList<String[]>();
		List<String> set = redisUtil.getList("BUSI_BASE_IP_PROXY_04", 0);
		for (String infos : set) {
			Map<String, Object> map = FastJsonUtil.toMap(infos);
			list.add(new String[]{map.get("proxyHost").toString(),map.get("proxyPort").toString()});
		}
		return list;
	}
	public static void main(String[] args) {
	    Spider.create(new JWSpiderBizImpl()).addUrl("https://www.vanilla-air.com/api/booking/flight-fare/list.json?__ts="+System.currentTimeMillis()+"&adultCount=1&childCount=1&couponCode=&currency=CNY&destination=OKA&infantCount=0&isMultiFlight=true&origin=NRT&targetMonth=201702&version=1.0")
	    .addPipeline(new JwFlightInfosPipeline())
	    .run();
	}
}
