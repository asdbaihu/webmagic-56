package com.wyy.spider.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruizn.base.utils.GlobalSettingBase;

/**
 * @author 代长伟
 */
public class GlobalSetting extends GlobalSettingBase {

	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory.getLog(GlobalSetting.class);
	
	/**
	 * 获取当前系统使用用户
	 * @return
	 */
	public static String getSystemCurrentUsename() {
		log.debug("");
		return getGlobalSetting().getProperty("system_current_usename");
	}
	
	/**
	 * 判断当前系统是否是开发模式
	 * @return
	 */
	public static boolean isDebugMode() {
		return "true".equals( getGlobalSetting().getProperty("debug_mode") ) ? true : false ;
	}
	
	/**
	 * 获取代理服务器地址
	 * @return
	 */
	public static String getProxyHost(){
		return getGlobalSetting().getProperty("proxy.proxyHost");
	}
	
	/**
	 * 获取代理服务器端口
	 * @return
	 */
	public static String getProxyPort(){
		return getGlobalSetting().getProperty("proxy.proxyPort");
	}
	
	/**
	 * 获取代理服务器隧道证书
	 * @return
	 */
	public static String getUserName(){
		return getGlobalSetting().getProperty("proxy.userName");
	}
	
	/**
	 * 获取代理服务器隧道密钥
	 * @return
	 */
	public static String getPassWord(){
		return getGlobalSetting().getProperty("proxy.passWord");
	}
	
	/**
	 * 获取连接池最大连接数
	 * @return
	 */
	public static String getMaxTotalPool(){
		return getGlobalSetting().getProperty("httpClient.pool.maxTotalPool");
	}
	
	
	/**
	 * 获取每个路由基础的连接数量
	 * @return
	 */
	public static String getDefaultMaxPerRoute(){
		return getGlobalSetting().getProperty("httpClient.pool.defaultMaxPerRoute");
	}
	
	
	/**
	 * 获取目标主机的最大连接数的最大连接数量
	 * @return
	 */
	public static String getMaxConPerRoute(){
		return getGlobalSetting().getProperty("httpClient.pool.maxConPerRoute");
	}
	
	/**
	 * 获取读取数据超时时间
	 * @return
	 */
	public static String getSocketTimeout(){
		return getGlobalSetting().getProperty("httpClient.socketTimeout");
	}
	
	/**
	 * 获取httpclient请求连接超时时间
	 * @return
	 */
	public static String getConnectionRequestTimeout(){
		return getGlobalSetting().getProperty("httpClient.connectionRequestTimeout");
	}
	
	/**
	 * 获取httpclient连接超时时间
	 * @return
	 */
	public static String getConnectTimeout(){
		return getGlobalSetting().getProperty("httpClient.connectTimeout");
	}
	
	/**
	 * 获取抓取航线信息
	 * @return
	 */
	public static String getSpiderAirLine(){
		return getGlobalSetting().getProperty("spider_air_line");
	}
	
	/**
	 * 获取抓取航线信息
	 * @return
	 */
	public static String getSpiderAirLine( String lineNum ){
		return getGlobalSetting().getProperty("spider_air_line_" + lineNum );
	}
	
	/**
	 * 取SPRING配置文件1
	 * @return
	 */
	public static String getConfigFile1(){
		return getGlobalSetting().getProperty("configFile1");
	}
	
	/**
	 * 取SPRING配置文件2
	 * @return
	 */
	public static String getConfigFile2(){
		return getGlobalSetting().getProperty("configFile2");
	}
	
	/**
	 * 代理ip切换时间开关
	 * @return
	 */
	public static String getProxySwitch(){
		return getGlobalSetting().getProperty("proxy.switch");
	}
	
	/**
	 * 选择代理ip位置开关
	 * @return
	 */
	public static String getProxySelect(){
		return getGlobalSetting().getProperty("proxy.select");
	}
	
	/**
	 * 航班信息在redis中存放的时间
	 * @return
	 */
	public static int getKeepOrginalTimeLimit(){
		return Integer.parseInt(getGlobalSetting().getProperty("keepOrginalTimeLimit"));
	}

	/**
	 * 获取redis数据库标号
	 * @return 0
	 */
	public static int getRedisIndex0(){
		return Integer.parseInt(getGlobalSetting().getProperty("REDIS_DB_INDEX_0"));
	}
	
	/**
	 * 获取redis数据库标号
	 * @return 1
	 */
	public static int getRedisIndex1(){
		return Integer.parseInt(getGlobalSetting().getProperty("REDIS_DB_INDEX_1"));
	}
	
	/**
	 * 获取redis数据库标号
	 * @return 2
	 */
	public static int getRedisIndex2(){
		return Integer.parseInt(getGlobalSetting().getProperty("REDIS_DB_INDEX_2"));
	}
	
	/**
	 * 获取redis数据库标号
	 * @return 11
	 */
	public static int getRedisIndex11(){
		return Integer.parseInt(getGlobalSetting().getProperty("REDIS_DB_INDEX_11"));
	}
	
	/**
	 * 获取热门航线
	 * @return
	 */
	public static String getHotAirLine(){
		return getGlobalSetting().getProperty("hot_air_line");
	}
	
	/**
	 * 获取爬取航班的限定价格
	 * @return
	 */
	public static int getFlightPrice(){
		return Integer.parseInt(getGlobalSetting().getProperty("flight_price"));
	}
	
	/**
	 * 获取爬取数据的过期时间
	 * @return
	 */
	public static int getSpiderDataExpireTime() {
		return Integer.parseInt(getGlobalSetting().getProperty("data_expire_time"));
	}
	
	/**
	 * 获取代理ip池的标号
	 * @return
	 */
	public static String getProxyIpPoolNumber(){
		return getGlobalSetting().getProperty("proxyIp_pool_number");
	}
	
	/**
	 * 获取代理ip池的标号
	 * @return
	 */
	public static int getRedisDbIndex( String dbIndex ){
		return Integer.parseInt( getGlobalSetting().getProperty("REDIS_DB_INDEX_" + dbIndex ) );
	}
	
	/**
	 * 获取代理ip在本地的路径
	 * @return
	 */
	public static String getProxyIpLocalpath(){
		return getGlobalSetting().getProperty("proxyIp_local_path");
	}
	
	/**
	 * 获取分割代理ip字符串时host在数组中的坐标
	 * @return
	 */
	public static int getProxyIpSplitHostIndex(){
		return Integer.parseInt(getGlobalSetting().getProperty("proxyIp_split_host_index"));
	}
	
	/**
	 * 获取分割代理ip字符串时port在数组中的坐标
	 * @return
	 */
	public static int getProxyIpSplitPortIndex(){
		return Integer.parseInt(getGlobalSetting().getProperty("proxyIp_split_port_index"));
	}
	
	/**
	 * 获取分割代理ip字符串时username在数组中的坐标
	 * @return
	 */
	public static int getProxyIpSplitUserNameIndex(){
		return Integer.parseInt(getGlobalSetting().getProperty("proxyIp_split_username_index"));
	}
	
	/**
	 * 获取分割代理ip字符串时password在数组中的坐标
	 * @return 
	 */
	public static int getProxyIpSplitPassWordIndex(){
		return Integer.parseInt(getGlobalSetting().getProperty("proxyIp_split_password_index"));
	}
	
	/**
	 * 获取当航班座位数为3个时减去的座位数
	 * @return 1
	 */
	public static int getFlightSeats3(){
		return Integer.parseInt(getGlobalSetting().getProperty("flight_seats_3"));
	}
	
	/**
	 * 获取当航班座位数为4或5个时减去的座位数
	 * @return 2
	 */
	public static int getFlightSeats4Or5(){
		return Integer.parseInt(getGlobalSetting().getProperty("flight_seats_4_5"));
	}
	
	/**
	 * 获取TK代理每次提取ip的数量
	 * @return
	 */
	public static int getProxyIpExtractNumber(){
		return Integer.parseInt(getGlobalSetting().getProperty("proxyIp_extract_number"));
	}
	
	/**
	 * 删除异常ip开关
	 * @return
	 */
	public static String getProxyIpExceptionDelete(){
		return getGlobalSetting().getProperty("proxyIp_exception_delete");
	}
	
	/**
	 * 获取遇到等待页面时暂停的时间
	 * @return
	 */
	public static int getWaitingPauseTime(){
		return Integer.parseInt(getGlobalSetting().getProperty("waiting_pause_time"));
	}
	
	/**
	 * 获取遇到等待页面时连续请求的次数
	 * @return
	 */
	public static int getWaitingRequestNumber(){
		return Integer.parseInt(getGlobalSetting().getProperty("waiting_request_number"));
	}
	
	
	
	/** 发送邮件信息 begin */
	/** 发送邮件信息 end */
	
	/** 发送短信信息 begin */
	/** 发送短信信息 end */
	
	
	
}
