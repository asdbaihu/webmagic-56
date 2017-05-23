package test;

import org.apache.http.HttpHost;

import com.wyy.spider.common.utils.GlobalSetting;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;

public class test implements PageProcessor{
	private Site site = Site.me()
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0")
			.addHeader("Accept", "application/json, text/plain, */*")
			.addHeader("Host", "www.vanilla-air.com")
			.addHeader("Referer", "https://www.vanilla-air.com/en/booking/")
			.addHeader("channel", "pc")
			.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
			.addHeader("Accept-Encoding", "gzip, deflate, br")
			.setRetryTimes(3).setSleepTime(1000);
	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
	   Json json = page.getJson();
	   
	   System.out.println(json);
	  
	}
	public static void main(String[] args) {
	    Spider.create(new test()).addUrl("https://www.vanilla-air.com/api/booking/flight-fare/list.json?__ts="+System.currentTimeMillis()+"&adultCount=1&childCount=1&couponCode=&currency=JPY&destination=OKA&infantCount=0&isMultiFlight=true&origin=NRT&targetMonth=201702&version=1.0")
	    .run();
	}
}
