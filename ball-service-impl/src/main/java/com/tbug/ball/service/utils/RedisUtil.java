package com.tbug.ball.service.utils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class RedisUtil {
	
	@Autowired
	private StringRedisTemplate template;
	
	public long addOrIncr(String key, long inc, Date expiry) {
		BoundValueOperations<String,String> userOps=template.boundValueOps(key);
		long result=userOps.increment(inc);
		userOps.expireAt(expiry);
		return result;
	}
	
	public long addOrIncr(String key, long inc) {
		BoundValueOperations<String,String> userOps=template.boundValueOps(key);
		long result=userOps.increment(inc);
		return result;
	}
	
	public long addOrIncr(String key, Date expiry) {
		return addOrIncr(key, 1L, expiry);
	}

	/**
	 * 如果key已经失效或者不存在，则返回null
	 * 业务逻辑控制是否转化为Object
	 * @param key
	 * @return
	 */
	public  String get(String key) {
		BoundValueOperations<String,String> userOps=template.boundValueOps(key);
		return userOps.get();
	}
	
	/**
	 * 传入对象，强转转化为JSON字符串对象，进行保存
	 * 不设置有效期
	 * @param key
	 * @param value
	 */
	public  void setValue(String key, Object value) {
		BoundValueOperations<String,String> userOps=template.boundValueOps(key);
		userOps.set(JSON.toJSONString(value));
	}
	
	
	public  void remove(String key) {
		template.delete(key);
	}

	/**
	 * 根据key获取当前的计数值，
	 * 如果不存在返回-1
	 * @param key
	 * @return
	 */
	public long getCounter(String key) {
		String count=get(key);
		if(StringUtils.isNumeric(count)){
			return Long.parseLong(count);
		}else{
			return -1;
		}
	}

	/**
	 * 获取key
	 * @param pattern
	 * @return
	 */
	public  Set<String> getMultiKeys(String pattern) {
		return template.keys(pattern);
	}
	
	
	/**
	 * value类型为set
	 * 添加value到指定的key
	 * @param key
	 * @param value
	 * @return
	 */
	public long setToSet(String key, String value) {
		BoundSetOperations<String, String> boundListOperations = template.boundSetOps(key);	
		return boundListOperations.add(value);
	}
	
	/**
	 * 获取指定的key的set中所有的值
	 * @param key
	 * @return
	 */
	public Set<String> getSetDatas(String key) {
		BoundSetOperations<String, String> boundListOperations = template.boundSetOps(key);
		Set<String> sets= boundListOperations.members();
		return sets;
	}
	
	/**
	 * value类型为list
	 * 添加value到指定的key
	 * @param key
	 * @param value
	 * @return
	 */
	public long setToListLeft(String key, String value){
		BoundListOperations<String,String> boundListOperations = template.boundListOps(key);
		return boundListOperations.leftPush(value);
	}
	
	/**
	 * 移除某个List中指定的一个值
	 * 默认等待60S
	 * @param key
	 * @param value
	 */
	public String removeListDataRight(String key) {
		BoundListOperations<String, String> boundListOperations = template.boundListOps(key);	
		return boundListOperations.rightPop(60, TimeUnit.SECONDS);
	}
	
	public String removeListRight(String key) {
		BoundListOperations<String, String> boundListOperations = template.boundListOps(key);	
		return boundListOperations.rightPop();
	}
	
	
	/**
	 * 获取指定的key的list中所有的值
	 * @param key
	 * @return
	 */
	public List<String> getListDatas(String key) {
		BoundListOperations<String, String> boundListOperations = template.boundListOps(key);	
		List<String> sets= boundListOperations.range(0, -1);
		return sets;
	}

	/**
	 * 移除某个Set中的指定值
	 * @param key
	 * @param value
	 */
	public void removeSetData(String key, String value) {
		BoundSetOperations<String, String> boundListOperations = template.boundSetOps(key);	
		boundListOperations.remove(value);
	}

	/**
	 * 移除某个List中指定的一个值
	 * @param key
	 * @param value
	 */
	public void removeListData(String key, String value) {
		BoundListOperations<String, String> boundListOperations = template.boundListOps(key);	
		boundListOperations.remove(1, value);
	}
	
	/**
	 * 判断key散列中是否有键为hKey的值
	 * @param key
	 * @param hKey
	 * @return
	 */
	public Boolean hasHashKey(String key, String hKey) {
		BoundHashOperations<String, String, String> boundHashOperations = template.boundHashOps(key);
		return boundHashOperations.hasKey(hKey);
	}
	
	/**
	 * 键值对hKey和hValue存入key散列
	 * @param key
	 * @param hKey
	 * @param hValue
	 */
	public void putHashData(String key, String hKey, String hValue){
		BoundHashOperations<String, String, String> boundHashOperations = template.boundHashOps(key);
		boundHashOperations.put(hKey, hValue);
	}
	
	/**
	 * 获取key散列中的所有hKey值
	 */
	public Set<String> getAllHashKey(String key){
		BoundHashOperations<String, String, String> boundHashOperations = template.boundHashOps(key);
		Set<String> hKeys = boundHashOperations.keys();
		return hKeys ;
	}
	
	/**
	 * 获取key散列中的所有hValue值
	 */
	public List<String> getAllHashData(String key){
		BoundHashOperations<String, String, String> boundHashOperations = template.boundHashOps(key);
		List<String> hValues = boundHashOperations.values();
		return hValues;
	}
	
	/**
	 * 获取key散列中键为hKey值
	 * @param key
	 * @param hKey
	 * @return
	 */
	public String getHashData(String key, String hKey){
		BoundHashOperations<String, String, String> boundHashOperations = template.boundHashOps(key);
		return boundHashOperations.get(hKey);
	}
	
	/**
	 * 删除key散列中键为hKey的键值对
	 * @param key
	 * @param hKey
	 */
	public void deleteHashData(String key, String hKey){
		BoundHashOperations<String, String, String> boundHashOperations = template.boundHashOps(key);
		boundHashOperations.delete(hKey);
	}

	
	/**
	 * 获取指定的key
	 * @param keys
	 * @return
	 */
	public static String getCacheKey(String... keys){
		StringBuffer sb = new StringBuffer("");
		for(String key : keys){
			sb.append(key).append("_");
		}
		String result = sb.toString();
		if(result.endsWith("_")){
			result=result.substring(0,result.length()-1);
		}
		return result;
	}
	
	
	/**
	 * 删除所有redis 数据
	 */
	public  void  deleteAll(){
		Set<String>  sets=getMultiKeys("*");
		template.delete(sets);
	}
	
	public Set<String> getMultiByParentKey(String parentKey){
		return template.keys(parentKey + "_*" );
	}
	
	
	public void renameKey(String oldKey,String newKey){
		template.rename(oldKey, newKey);
	}
	
}
