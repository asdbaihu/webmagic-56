package com.wyy.spider.common.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	
	private static final Logger logger = Logger.getLogger(JedisUtil.class);
	
//	private static String ADDR = "192.168.2.176";
	private static String ADDR = "115.29.146.148";

    private static int PORT = 6379;

//    private static String AUTH = null;
    private static String AUTH = "20160627$6379";

    private static int MAX_ACTIVE = 300;

    private static int MAX_IDLE = 200;

    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    private static Jedis jedis = null;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            init();
        } catch (Exception e) {
            logger.error("初始化Redis出错，" + e);
        }
    }

    /**
     * 初始化连接池
     * 
     * @see [类、类#方法、类#成员]
     */
    private synchronized static void init() {
//        ADDR = PropertyUtils.getValue("redis.addr");
//        PORT = Integer.valueOf(PropertyUtils.getValue("redis.port"));
//        AUTH = PropertyUtils.getValue("redis.auth");
//        MAX_ACTIVE = Integer.valueOf(PropertyUtils
//                .getValue("redis.pool.max_active"));
//        MAX_IDLE = Integer.valueOf(PropertyUtils
//                .getValue("redis.pool.max_idle"));
//        MAX_WAIT = Integer.valueOf(PropertyUtils
//                .getValue("redis.pool.max_wait"));
//        TIMEOUT = Integer.valueOf(PropertyUtils.getValue("redis.pool.timeout"));

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(TEST_ON_BORROW);
        config.setMaxTotal(MAX_ACTIVE);
        jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
    }

    /**
     * 获取Jedis实例
     * 
     * @return
     */
    private static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            } else {
                init();
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            logger.error("获取Redis实例出错，" + e);
        }
        return jedis;
    }

    /**
     * 设置单个值
     * 
     * @param key
     * @param value
     * @return
     */
    public static String set(String key, String value) {
        return set(key, value, null);
    }

    /**
     * 设置单个值，并设置超时时间
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @param timeout
     *            超时时间（秒）
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String set(String key, String value, Integer timeout) {
        String result = null;

        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.set(key, value);
            if (null != timeout) {
                jedis.expire(key, timeout);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 获取单个值
     * 
     * @param key
     * @param dbIndex 
     * @return
     */
    public static String get(String key, int dbIndex) {
        String result = null;
        Jedis jedis = JedisUtil.getJedis();
        if (jedis == null) {
            return result;
        }
        try {
        	jedis.select(dbIndex);
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 删除redis中数据
     * 
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean del(String key) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = JedisUtil.getJedis();
        if (null == jedis) {
            return Boolean.FALSE;
        }
        try {
            jedis.del(key);
        } catch (Exception e) {
            logger.error("删除redis数据出错，" + e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 追加
     * 
     * @param key
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Long append(String key, String value) {
        Long result = Long.valueOf(0);
        Jedis jedis = JedisUtil.getJedis();
        if (null == jedis) {
            return result;
        }
        try {
            result = jedis.append(key, value);
        } catch (Exception e) {
            logger.error("追加redis数据出错，" + e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 检测是否存在
     * 
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Boolean exists(String key) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = JedisUtil.getJedis();
        if (null == jedis) {
            return result;
        }
        try {
            result = jedis.exists(key);
        } catch (Exception e) {
            logger.error("检查是否存在出错：，" + e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

	public static List<String> getList(String key,int dbIndex) {
		List<String> list = null;
		Jedis jedis		  = null;
		try {
			jedis = JedisUtil.getJedis();
			if (null == jedis) {
	            return list;
	        }
			jedis.select(dbIndex);
			list = jedis.lrange(key, 0, jedis.llen(key));
		} catch (Exception e) {
			logger.error("获取List型数据捕获异常："+e);
		}finally {
            if (null != jedis) {
                jedis.close();
            }
        }
		
		return list;
	}
	
	/**
	 * 获取Hash类型数据
	 * @param hkey
	 * @param dbIndex
	 * @return
	 * Map<String,String>
	 *
	 */
	public static Map<String, String> hGetAll(String hkey,int dbIndex) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Jedis jedis = JedisUtil.getJedis();
			if (null == jedis) {
				return map;
			}
			jedis.select(dbIndex);
			map = jedis.hgetAll(hkey);
		} catch (Exception e) {
			logger.error("获取Hash型数据捕获异常："+e);
		} finally {
            if (null != jedis) {
                jedis.close();
            }
        }
		return map;
	}
	
	/**
	 * Zset类型 ，作记分用
	 * @param key
	 * @param scoreMembers
	 * @param dbIndex
	 * @return
	 * long
	 *
	 */
	public static long zadd(String key,Map<String,Double> scoreMembers,int dbIndex) {
		Long zadd = -1L;
		try {
			Jedis jedis = JedisUtil.getJedis();
			if (null == jedis) {
				return zadd;
			}
			jedis.select(dbIndex);
			zadd = jedis.zadd(key, scoreMembers);
		} catch (Exception e) {
			logger.error("添加Zset数据异常："+e);
		} if (null != jedis) {
            jedis.close();
        }
		return zadd;
	}
	
	/**
	 * 排序
	 * @param key
	 * @param dbIndex
	 * @return
	 * Map<String,Integer>
	 *
	 */
	public static Map<String, Integer> zsorts(String key,int dbIndex) {
		Map<String,Integer> map = new LinkedHashMap<String, Integer>();
		try {
			Jedis jedis = JedisUtil.getJedis();
			if (null == jedis) {
				return map;
			}
			jedis.select(dbIndex);
			Set<String> set = jedis.zrangeByScore(key, 0, 10000);
			for (String s : set) {
				map.put(s,jedis.zscore(key, s).intValue());
			}
		} catch (Exception e) {
			logger.error("获取List型数据捕获异常："+e);
		} finally {
            if (null != jedis) {
                jedis.close();
            }
        }
	    return map;
	}
	/**
	 * 获取set型数据
	 * @param key
	 * @param dbIndex
	 * @return
	 * Set<String>
	 *
	 */
	public static Set<String> sGetAll(String key,int dbIndex) {
		Set<String> set = new HashSet<String>();
		try {
			Jedis jedis = JedisUtil.getJedis();
			if (null == jedis) {
				return set;
			}
			jedis.select(dbIndex);
			set = jedis.smembers(key);
		} catch (Exception e) {
			logger.error("获取Set型数据捕获异常："+e);
		} if (null != jedis) {
            jedis.close();
        }
		return set;
	}
	
	/**
	 * 匹配键
	 * @param string
	 * @return
	 * Set<String>
	 *
	 */
	public Set<String> keys(String key,int dbIndex) {
		Set<String> set = new HashSet<String>();
		try {
			Jedis jedis = JedisUtil.getJedis();
			if (null == jedis) {
				return set;
			}
			jedis.select(dbIndex);
			set = jedis.keys(key);
		} catch (Exception e) {
			logger.error("获取Set型数据捕获异常："+e);
		} if (null != jedis) {
            jedis.close();
        }
		return set;
	}
}
