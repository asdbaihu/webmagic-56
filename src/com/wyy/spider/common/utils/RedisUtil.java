package com.wyy.spider.common.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ruizn.base.utils.StringUtil;

/**
 * redis通用工具类
 * 
 * @author lzh
 * 
 */
public class RedisUtil {
	    private static final Logger log = Logger.getLogger(RedisUtil.class); 
	    
	    private static final String DELETE_KEY_1 		= "DELETE_KEY_1" ;
	    private static final String DELETE_KEY_2 		= "DELETE_KEY_2" ;
	    
	    private static final String HAS_KEY 		= "HAS_KEY" ;
	    
	    private static final String STRING_GET 		= "STRING_GET" ;
	    private static final String STRING_SET 		= "STRING_SET" ;
	    
	    private static final String HASH_GET 		= "HASH_GET" ;
	    private static final String HASH_GET_ALL	= "HASH_GET_ALL" ;
	    private static final String HASH_SET 		= "HASH_SET" ;
	    private static final String HASH_SET_ALL	= "HASH_SET_ALL" ;
	    
	    private static final String LIST_IN 		= "LIST_IN" ;
	    private static final String LIST_OUT 		= "LIST_OUT" ;
	    private static final String GET_LIST 		= "GET_LIST" ;
	    private static final String LIST_SIZE 		= "LIST_SIZE" ;
	    private static final String REMOVE_KEY  	= "REMOVE_KEY" ;
	    
	    private static final String SET_GET 		= "SET_GET" ;
	    private static final String SET_SET 		= "SET_SET" ;
	    private static final String SET_SET_ALL		= "SET_SET_ALL" ;
	    private static final String SET_SET_ALL2	= "SET_SET_ALL2" ;
	    
	    //注入工厂
		private JedisConnectionFactory jedisConnectionFactory;
		
		public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
			this.jedisConnectionFactory = jedisConnectionFactory;
		}
		
		private RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();  

		private RedisTemplate<String,String> getTemplate(int dbIndex){
		    //选择DB
			jedisConnectionFactory.setDatabase(dbIndex);
			try {
				 //模板设置
				 redisTemplate.setConnectionFactory(jedisConnectionFactory);
				 //初始化键值序列化类型
				 redisTemplate.setKeySerializer(new StringRedisSerializer());
				 redisTemplate.setValueSerializer(new StringRedisSerializer());
				 redisTemplate.setHashKeySerializer(new StringRedisSerializer());
				 redisTemplate.setHashValueSerializer(new StringRedisSerializer());
				 redisTemplate.afterPropertiesSet();
				 return redisTemplate;
			} catch (Exception e) {
				 log.error("指定DB—index的 Redis模板类初始化失败！"+e);
				 throw new RuntimeException("指定DB—index的 Redis模板类初始化失败！"+e);
			}
		}

		/**
		 * redis 数据处理 
		 * @param rdto
		 */
		private synchronized Object dealRedisValue( RedisDto rdto ) {
			Object rValue 	= null ;
			
			//获取模板类
			redisTemplate	= getTemplate( rdto.getDbIndex() );
			 
			switch( rdto.getDataType() ) {
				case DELETE_KEY_1 : 
					//操作数据
					redisTemplate.delete(rdto.getKey());
					break ;
				case DELETE_KEY_2 :
					//操作数据
					redisTemplate=getTemplate(rdto.getDbIndex());
		    		redisTemplate.opsForHash().delete(rdto.getKey(), rdto.getValue());
		    		break;
				case HAS_KEY : 
					//操作数据
					rValue = redisTemplate.hasKey( rdto.getKey() ) ;
					break ;
				
				case STRING_GET : 
					//操作数据
					rValue = redisTemplate.opsForValue().get( rdto.getKey() );
					break ;
					
				case STRING_SET : 
					if( StringUtil.isNotBlank( rdto.getExpireTime() ) ){
						//操作数据
						redisTemplate.opsForValue().set(rdto.getKey(), rdto.getValue() );
						redisTemplate.expire(rdto.getKey(), rdto.getExpireTime(), TimeUnit.SECONDS);
					}else {
						//操作数据
						redisTemplate.opsForValue().set(rdto.getKey(), rdto.getValue() );
					}
					break ;
				
				case LIST_IN : 
					//操作数据
					rValue =  redisTemplate.opsForList().rightPush(rdto.getKey(), rdto.getValue()) ;
					break ;
					
				case LIST_OUT : 
					//操作数据
					rValue =  redisTemplate.opsForList().leftPop( rdto.getKey() ) ;
					break ;
					
				case REMOVE_KEY :
					// 操作数据
					rValue = redisTemplate.opsForList().remove(rdto.getKey(),0, rdto.getValue());
					break;
					
				case GET_LIST:
					// 操作数据
					rValue = redisTemplate.opsForList().range(rdto.getKey(), 0, -1);
					break ;
					
				case LIST_SIZE : 
					//操作数据
					rValue =  redisTemplate.opsForList().size( rdto.getKey() ) ;
					break ;
					
				case HASH_GET : 
					//操作数据
					rValue = redisTemplate.opsForHash().get( rdto.getHashKey(), rdto.getKey() );
					break ;
				
				case HASH_GET_ALL : 
					//操作数据
					rValue = redisTemplate.opsForHash().entries( rdto.getKey()) ;
					break ;
				
				case HASH_SET : 
					
					if( StringUtil.isBlank( rdto.getExpireDate() ) ){
						//操作数据
						redisTemplate.opsForHash().put(rdto.getHashKey(), rdto.getKey(), rdto.getValue() );
					}else {
						if(!redisTemplate.hasKey( rdto.getHashKey() )){
						     redisTemplate.opsForHash().put( rdto.getHashKey(), rdto.getKey(), rdto.getValue() );
						     redisTemplate.expireAt( rdto.getHashKey(), rdto.getExpireDate() );
						}else{
							 redisTemplate.opsForHash().put(rdto.getHashKey(), rdto.getKey(), rdto.getValue() );
						}
					}
					
					break ;
					
				case HASH_SET_ALL : 
					//操作数据
					if(!hasKey(rdto.getKey(), rdto.getDbIndex() )){
						  redisTemplate.opsForHash().putAll(rdto.getKey(), rdto.getMap() );
						  if( rdto.getExpireTime() > 0L ){
							  redisTemplate.expire(rdto.getKey(), rdto.getExpireTime(), TimeUnit.SECONDS);
						  }
					}else{
						  redisTemplate.opsForHash().putAll(rdto.getKey(), rdto.getMap() );
					}
					break ;	
					
				case SET_GET : 
					//操作数据
					rValue = redisTemplate.opsForSet().members( rdto.getKey() );
					break ;
				
				case SET_SET : 
					//操作数据
					if(!hasKey( rdto.getKey(), rdto.getDbIndex() )){
						redisTemplate.opsForSet().add( rdto.getKey(), rdto.getValue() );
						if( rdto.getExpireTime() > 0L ){
							redisTemplate.expire( rdto.getKey(), rdto.getExpireTime(), TimeUnit.SECONDS);
						} 
					}else{
						redisTemplate.opsForSet().add( rdto.getKey(), rdto.getValue() );
//						redisTemplate.opsForSet().intersectAndStore( rdto.getKey(), rdto.getKey(), rdto.getValue() );
					}
					break ;
					
				case SET_SET_ALL :	
					//操作数据
					if(!hasKey(rdto.getKey(), rdto.getDbIndex() )){
						for (String o : rdto.getList() ) {
							redisTemplate.opsForSet().add( rdto.getKey(), o);
						}
						if(rdto.getExpireTime()>0L){
							redisTemplate.expire( rdto.getKey(), rdto.getExpireTime(), TimeUnit.SECONDS);
						} 
					}else{
						for (String o : rdto.getList() ) {
							redisTemplate.opsForSet().add( rdto.getKey(), o);
						} 
					}
					
					break ;
					
				case SET_SET_ALL2 :	
					//操作数据
					if(!hasKey(rdto.getKey(), rdto.getDbIndex() )){
						for (String o : rdto.getSet()) {
							redisTemplate.opsForSet().add(rdto.getKey(), o);
						}
						if(rdto.getExpireTime()>0L){
							redisTemplate.expire(rdto.getKey(), rdto.getExpireTime(), TimeUnit.SECONDS);
						} 
					}else{
						for (String o : rdto.getSet()) {
							redisTemplate.opsForSet().add(rdto.getKey(), o);
						} 
					}
					
					break ;	
			}
			
			return rValue ;
		}
		
		/**
		 * 键值删除,删除整个hashKey中的数据
		 * @param key
		 * @param dbIndex
		 */
	    public void delete(String key,int dbIndex){
	    	  try {
				//获取模板类
//				  redisTemplate=getTemplate(dbIndex);
//				  redisTemplate.delete(key);
				  dealRedisValue( new RedisDto( key , dbIndex, DELETE_KEY_1 ) ) ;
			} catch (Exception e) {
				log.error(e);
			}
	    }
	    
	    
	    /**
	     * 键值删除,删除hashKey中的某一个key的数据
	     * @param hashKey
	     * @param keySet
	     * @param dbIndex
	     */
	    public void delete(String hashKey,String key,int dbIndex){
	    	try {
	    		//获取模板类
//	    		redisTemplate=getTemplate(dbIndex);
//	    		redisTemplate.opsForHash().delete(hashKey, keySet);
	    		dealRedisValue( new RedisDto( hashKey, key , dbIndex, DELETE_KEY_2 ) ) ;
	    	} catch (Exception e) {
	    		log.error(e);
	    	}
	    }
	    
	    /**
	     * 删除list中的指定value
	     * @param key
	     * @param value
	     * @param dbIndex
	     */
	    public void remove(String key,String value,int dbIndex){
	    	try {
	    		//获取模板类
	    		dealRedisValue( new RedisDto( key, value , dbIndex, REMOVE_KEY ) ) ;
	    	} catch (Exception e) {
	    		log.error(e);
	    	}
	    }
	    /**
		 * 判断数据是否存在
		 * @param key
		 * @param dbIndex
		 * @return
		*/
		public boolean hasKey(String key,int dbIndex){
			try {
//				//获取模板类
//				redisTemplate=getTemplate(dbIndex);
//				if(redisTemplate.hasKey(key)&&redisTemplate.keys(key)!=null){
//					return true;
//				}else{
//					return false;
//				}
				Object obj =  dealRedisValue( new RedisDto( key , dbIndex, HAS_KEY ) ) ;
				return obj == null ? false : (Boolean)obj  ;
			} catch (Exception e) {
				log.error("存储Hash类型数据失败！"+e);
				throw new RuntimeException(e);
			}
		}
	    
		/**
	     * 指定DB查询
	     * @param key
	     * @param dbIndex
	     * @return json
	     */
		public String getString(String key,int dbIndex){
			try {
//				//获取模板类
//				 redisTemplate=getTemplate(dbIndex);
//				//操作数据
//				return redisTemplate.opsForValue().get(key);
				
				Object obj =  dealRedisValue( new RedisDto( key , dbIndex, STRING_GET ) ) ;
				return obj == null ? "" : (String)obj  ;
			} catch (Exception e) {
				log.error("读取数据失败！"+e);
				throw new RuntimeException(e);
			}
		}
		
		/**
		  * 选择db 存数据
		  * @param key
		  * @param value
		  * @param time
		  * @param dbindex
		*/
		public void setString(String key,String value,int dbIndex){
			 try {
//				 //获取模板类
//				 redisTemplate=getTemplate(dbIndex);
//				 //操作数据
//				 redisTemplate.opsForValue().set(key, value);
				 
				 dealRedisValue( new RedisDto( key , value, dbIndex, STRING_SET ) ) ;
			 } catch (Exception e) {
				 log.error("存储数据失败！"+e);
				 throw new RuntimeException(e);
			 }
		}
		
		/**
	     * 选择db 存数据
	     * @param key
	     * @param value
	     * @param time 单位：min
	     * @param dbindex
	     */
		public void setString(String key,String value,long time,int dbIndex){
			try {
//				 //获取模板类
//				 redisTemplate=getTemplate(dbIndex);
//				 //操作数据
//				 if(!hasKey(key, dbIndex)){
//					 redisTemplate.opsForValue().set(key, value);
//					 redisTemplate.expire(key, time,TimeUnit.SECONDS);
//				 }else{
//				     BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(key);
//					 Long expire = ops.getExpire();
//					 ops.set(value, expire, TimeUnit.SECONDS);
//				 }
				dealRedisValue( new RedisDto( key , value, time, dbIndex, STRING_SET ) ) ;
			} catch (Exception e) {
				log.error("存储数据失败！"+e);
				throw new RuntimeException(e);
			}
		}
	    
		/**
		 * Hash类型读取数据 
		 * @param hashKey
		 * @param key
		 * @param dbIndex
		 * @return
		 */
		public String getHash(String hashKey,String key,int dbIndex){
			try {
//				//获取模板类
//				redisTemplate=getTemplate(dbIndex);
//			  	return  (String)redisTemplate.opsForHash().get(hashKey, key);
				RedisDto dto = new RedisDto() ;
				dto.setHashKey(hashKey);
				dto.setKey(key);
				dto.setDbIndex(dbIndex);
				dto.setDataType( HASH_GET );
				
				Object obj =  dealRedisValue( dto ) ;
			  	return obj == null ? "" : (String)obj  ;
			} catch (Exception e) {
				log.error("存储Hash类型数据失败！"+e);
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * Hash类型读取数据 
		 * @param hashKey
		 * @param dbIndex
		 * @return
		 */
		public Map<Object, Object> getHashList(String hashKey,int dbIndex){
			try {
				//获取模板类
//				redisTemplate=getTemplate(dbIndex);
//				return  redisTemplate.opsForHash().entries(hashKey);
				
				Object obj =  dealRedisValue( new RedisDto( hashKey , dbIndex, HASH_GET_ALL ) )  ;
				return obj == null ? null : (Map)obj  ;
			} catch (Exception e) {
			  log.error("存储Hash类型数据失败！"+e);
			  throw new RuntimeException(e);
			}
		}
		
		/**
		 * Hash类型存储数据 
		 * @param key
		 * @param K
		 * @param V
		 * @param time 单位：分钟
		 * @param dbIndex
	   */
		public void setHash(String hashKey,String key,Object value,int dbIndex){
		     try {
				//获取模板类
//				redisTemplate=getTemplate(dbIndex);
//				redisTemplate.opsForHash().put(hashKey, key, value);
				
				RedisDto dto = new RedisDto() ;
				dto.setHashKey(hashKey);
				dto.setKey(key);
				dto.setValue( value.toString() );
				dto.setDbIndex(dbIndex);
				dto.setDataType( HASH_SET );
				
				dealRedisValue( dto ) ;
			} catch (Exception e) {
				log.error("存储Hash类型数据失败！"+e);
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * Hash类型存储数据 
		 * @param key
		 * @param K
		 * @param V
		 * @param time 单位：分钟
		 * @param dbIndex
	   */
		public void setHash(String hashKey,String key,Object value,Date date,int dbIndex){
		     try {
				//获取模板类
//				 redisTemplate=getTemplate(dbIndex);
//				 if(!redisTemplate.hasKey(hashKey)){
//				     redisTemplate.opsForHash().put(hashKey, key, value);
//				     redisTemplate.expireAt(hashKey, date);
//				 }else{
//					 redisTemplate.opsForHash().put(hashKey, key, value);
//				 }
				 
				RedisDto dto = new RedisDto() ;
				dto.setHashKey(hashKey);
				dto.setKey(key);
				dto.setValue( value.toString() );
				dto.setExpireDate( date );
				dto.setDbIndex(dbIndex);
				dto.setDataType( HASH_SET );
				
				dealRedisValue( dto ) ;
					
			} catch (Exception e) {
				log.error("存储Hash类型数据失败！"+e);
				throw new RuntimeException(e);
			}
		}	
		
		 /**
		  * @param key
		  * @param map
		  * @param time 
		  * @param dbIndex
		  */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void setAllHash(String key, Map map ,long time,int dbIndex){
			  try {
				  //获取模板类
//				  redisTemplate=getTemplate(dbIndex);
//				  if(!hasKey(key, dbIndex)){
//					  redisTemplate.opsForHash().putAll(key, map);
//					  redisTemplate.expire(key, time, TimeUnit.SECONDS);
//				  }else{
//					  redisTemplate.opsForHash().putAll(key, map);
//				  }
				  
				  RedisDto dto = new RedisDto() ;
				  dto.setKey(key);
				  dto.setMap(map);
				  dto.setExpireTime( time );
				  dto.setDbIndex(dbIndex);
				  dto.setDataType( HASH_SET_ALL );
					
				  dealRedisValue( dto ) ;
					
			  } catch (Exception e) {
				  log.error("存储Hash类型数据失败！"+e);
				  throw new RuntimeException(e);
			  }
		}
		
		/**
		 * 获取set集合
		 * @param key
		 * @param dbIndex
		 * @return
		 */
		public Set<String> getSet(String key,int dbIndex){
			try {
				//获取模板类
//				redisTemplate=getTemplate(dbIndex);
//				return redisTemplate.opsForSet().members(key);
				
				Object obj =  dealRedisValue( new RedisDto( key , dbIndex, SET_GET ) ) ;
				return obj == null ? null : (Set<String>)obj  ;
				
			} catch (Exception e) {
				log.error("读取Set类型数据失败！"+e);
				throw new RuntimeException(e);
			}
		}
	  
		/**
		 * 添加set集合元素
		 * @param key
		 * @param String
		 * @param time 单位：min
		 * @param dbIndex
		 */
		public void setAllSet(String key,String value,long time,int dbIndex){
			try {
			  //获取模板类
//			  redisTemplate=getTemplate(dbIndex);
//			  if(!hasKey(key, dbIndex)){
//				  redisTemplate.opsForSet().add(key, value);
//				  if(time>0L){
//					  redisTemplate.expire(key, time, TimeUnit.SECONDS);
//				  } 
//			  }else{
//				  redisTemplate.opsForSet().add(key, value);
//				  redisTemplate.opsForSet().intersectAndStore(key, key, value);
//			  }
			  
			  dealRedisValue( new RedisDto( key , value, time, dbIndex, SET_SET ) ) ;
			  
			} catch (Exception e) {
			  log.error("存储Set类型数据失败！"+e);
			  throw new RuntimeException(e);
			}
		}
	  
		/**
		 * 添加set集合元素
		 * @param key
		 * @param List<String>
		 * @param time 单位：min
		 * @param dbIndex
		 */
		public void setAllSet(String key,List<String> value,long time,int dbIndex){
			try {
				//获取模板类
//				redisTemplate=getTemplate(dbIndex);
//				if(!hasKey(key, dbIndex)){
//					for (String o : value) {
//						redisTemplate.opsForSet().add(key, o);
//					}
//					if(time>0L){
//						redisTemplate.expire(key, time, TimeUnit.SECONDS);
//					} 
//				}else{
//					for (String o : value) {
//						redisTemplate.opsForSet().add(key, o);
//					} 
//				}
				
				RedisDto dto = new RedisDto() ;
				dto.setKey(key);
				dto.setList( value );
				dto.setExpireTime( time );
				dto.setDbIndex(dbIndex);
				dto.setDataType( SET_SET_ALL );
					
				dealRedisValue( dto ) ;
				  
			} catch (Exception e) {
				log.error("存储Set类型数据失败！"+e);
				throw new RuntimeException(e);
			}
		}
	 
		/**
		 * 添加set集合元素
		 * @param key
		 * @param List<String>
		 * @param time 单位：min
		 * @param dbIndex
		 */
		public void setAllSet(String key,Set<String> value,long time,int dbIndex){
			try {
//				//获取模板类
//				redisTemplate = getTemplate(dbIndex);
//				if(!hasKey(key, dbIndex)){
//					for (String o : value) {
//						redisTemplate.opsForSet().add(key, o);
//					}
//					if(time>0L){
//						redisTemplate.expire(key, time, TimeUnit.SECONDS);
//					} 
//				}else{
//					for (String o : value) {
//						redisTemplate.opsForSet().add(key, o);
//					} 
//				}
				
				RedisDto dto = new RedisDto() ;
				dto.setKey(key);
				dto.setSet( value );
				dto.setExpireTime( time );
				dto.setDbIndex( dbIndex );
				dto.setDataType( SET_SET_ALL2 );
				
				dealRedisValue( dto ) ;
				
			} catch (Exception e) {
				  log.error("存储Set类型数据失败！"+e);
				  throw new RuntimeException(e);
			}
		}
		  
		/** 
	     * 入队 
	     * @param key 
	     * @param value 
	     * @return 
	     */  
	  	public Long inQueue(String key, String value,int dbIndex) {  
	    	 try {
				  //获取模板类
//				  redisTemplate=getTemplate(dbIndex);
//				  return redisTemplate.opsForList().rightPush(key, value);  
	    		 Object obj =  dealRedisValue( new RedisDto( key , value, dbIndex, LIST_IN ) ) ;
				 return obj == null ? null : (Long)obj  ;
			  } catch (Exception e) {
				  log.error("入队数据失败:"+e);
				  throw new RuntimeException(e);
			  }
	    }  
	  
	    /** 
	     * 出队 
	     * @param key 
	     * @return 
	     */  
	    public String outQueue(String key,int dbIndex) { 
	   	 try {
//			  //获取模板类
//			  redisTemplate=getTemplate(dbIndex);
//			  return redisTemplate.opsForList().leftPop(key);  
	   		 Object obj =  dealRedisValue( new RedisDto( key , dbIndex, LIST_OUT ) )  ;
			 return obj == null ? "" : (String)obj  ;
		  } catch (Exception e) {
			  log.error("出队数据失败:"+e);
			  throw new RuntimeException(e);
		  }
	    } 
	  
	    /**
	     * 获取list集合
	     * @param key
	     * @param dbIndex
	     * @return
	     */
	    public List<String> getList(String key, int dbIndex){
	    	
	    	try {
				Object obj = dealRedisValue( new RedisDto( key , dbIndex, GET_LIST ) );
				return (List<String>) (obj == null ? "" : obj);
			} catch (Exception e) {
				log.error("获取list集合失败:"+e);
				throw new RuntimeException(e);
			}
	    }
	    /** 
	     * 队列长度 
	     * @param key 
	     * @return 
	     */  
	    public Long lengthQueue(String key,int dbIndex) { 
	   	 try {
			  //获取模板类
//			  redisTemplate=getTemplate(dbIndex);
//			  return redisTemplate.opsForList().size(key);  
	   		 Object obj = dealRedisValue( new RedisDto( key , dbIndex, LIST_SIZE ) ) ;
	   		 return obj == null ? null : (Long)obj  ;
		  } catch (Exception e) {
			  log.error("入队数据失败:"+e);
			  throw new RuntimeException(e);
		  }
	    }  
}

class RedisDto {
	//
	private String 	key 		;
	//
	private String hashKey 		;
	//
	private String 	value 		;
	//
	private Long 	expireTime 	;
	//
	private Date 	expireDate 	;
	//
	private Map 	map 		;
	// 
	private List<String> list 	;
	// 
	private Set<String> set 	;
	//
	private int 	dbIndex 	;
	//
	private String 	dataType 	;
		
	public RedisDto() {
		super();
	}

	public RedisDto(String key,  int dbIndex,
			String dataType) {
		super();
		this.key = key;
		this.dbIndex = dbIndex;
		this.dataType = dataType;
	}
	
	public RedisDto(String key, String value, int dbIndex,
			String dataType) {
		super();
		this.key = key;
		this.value = value;
		this.dbIndex = dbIndex;
		this.dataType = dataType;
	}
	
	public RedisDto(String key, String value, Long expireTime, int dbIndex,
			String dataType) {
		super();
		this.key = key;
		this.value = value;
		this.expireTime = expireTime;
		this.dbIndex = dbIndex;
		this.dataType = dataType;
	}

	
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getValue() {
		return value;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public int getDbIndex() {
		return dbIndex;
	}


	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}


	public String getDataType() {
		return dataType;
	}


	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
}
