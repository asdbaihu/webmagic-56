package com.wyy.spider.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ruizn.base.exception.BaseException;

public class BootApp {

	public static void main(String[] args) throws BaseException {
		initApp();
	}

	public static void initApp() {
		new ClassPathXmlApplicationContext(
	    		new String[] {"classpath:springConfig/applicationContext-redis.xml"
	    					,"classpath:springConfig/applicationContext-job.xml"
	    				      ,"classpath:springConfig/applicationContext-boot.xml"
	    					 } ) ;
	}
	
}
