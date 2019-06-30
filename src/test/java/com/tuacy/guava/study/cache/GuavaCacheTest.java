package com.tuacy.guava.study.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @name: GuavaCacheTest
 * @author: tuacy.
 * @date: 2019/6/28.
 * @version: 1.0
 * @Description:
 */
public class GuavaCacheTest {


	@Test
	public void cacheTest() {

		// 通过CacheBuilder构建一个缓存实例
		Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100) // 设置缓存的最大容量
												  .expireAfterWrite(1, TimeUnit.MINUTES) // 设置缓存在写入一分钟后失效
												  .concurrencyLevel(10) // 设置并发级别为10
												  .recordStats() // 开启缓存统计
												  .build();
		// 放入缓存
		cache.put("key", "value");
		cache.put("key", "tuacy");
		// 获取缓存
		String value = cache.getIfPresent("key");
	}

}
