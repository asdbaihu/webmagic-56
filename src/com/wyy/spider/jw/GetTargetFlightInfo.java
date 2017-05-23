package com.wyy.spider.jw;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class GetTargetFlightInfo implements PageProcessor{

	/**
	 * 设置标头相关信息
	 */
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
	public void process(Page page) {
		
		
	}

	@Override
	public Site getSite() {
		return site;
	}

}
