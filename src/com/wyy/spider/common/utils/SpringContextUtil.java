package com.wyy.spider.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * 获取bean实例util
 * @author 冯淮港
 *
 */
public class SpringContextUtil implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext){
		SpringContextUtil.applicationContext =applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	public static Object getBean(String name) throws BeansException{
		return applicationContext.getBean(name);
	}
}

	
 
 