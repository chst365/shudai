package com.shudailaoshi.manager.cache.shiro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shudailaoshi.dao.redis.RedisRepository;

/**
 * @author Liaoyifan
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class ShiroRedisCacheManager implements CacheManager {

	private static final Logger LOG = LoggerFactory.getLogger(ShiroRedisCacheManager.class);

	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

	@Autowired
	private RedisRepository redisRepository;
	@Value("${redis.defaultExpireTime}")
	private long defaultExpireTime;

	@Override
	public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
		LOG.debug("获取名称为: " + cacheName + " 的RedisCache实例");
		Cache<K, V> cache = caches.get(cacheName);
		if (cache == null) {
			cache = new ShiroRedisCache<K, V>(redisRepository, cacheName, this.defaultExpireTime);
			caches.put(cacheName, cache);
		}
		return cache;
	}

	public ConcurrentMap<String, Cache> getCaches() {
		return caches;
	}

}
