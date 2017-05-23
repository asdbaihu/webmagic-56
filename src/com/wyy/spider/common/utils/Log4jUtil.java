package com.wyy.spider.common.utils;

import org.apache.log4j.PropertyConfigurator;

public class Log4jUtil {
   public static final String log_path = "src/log4j.properties";
   
   static{
	   PropertyConfigurator.configure(log_path); 
   }
}
