package com.shudailaoshi.manager.cache.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.shudailaoshi.dao.redis.RedisRepository;

/**
 * @author Liaoyifan
 *
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class ShiroRedisCache<K, V> implements Cache<K, V> {

	private static final Logger LOG = LogManager.getLogger(ShiroRedisCache.class);

	private RedisRepository redisRepository;
	private long defaultExpireTime;

	public ShiroRedisCache(RedisRepository redisRepository, String cacheName, long defaultExpireTime) {
		this.redisRepository = redisRepository;
		this.cacheName = cacheName;
		this.defaultExpireTime = defaultExpireTime;
	}

	private String cacheName;
	private String keyPrefix = "shiro_cache:";
	private String keyPattern = this.keyPrefix + "*";

	private String getRedisKey(K key) {
		return new StringBuilder(this.keyPrefix).append(cacheName).append(":").append(key).toString();
	}

	@Override
	public V get(K key) throws CacheException {
		LOG.debug("ShiroRedisCache.get [" + key + "]");
		try {
			if (key == null)
				return null;
			else
				return (V) redisRepository.get(this.getRedisKey(key));
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public V put(K key, V value) throws CacheException {
		LOG.debug("ShiroRedisCache.put [" + key + "]");
		try {
			redisRepository.set(this.getRedisKey(key), value, this.defaultExpireTime);
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public V remove(K key) throws CacheException {
		LOG.debug("ShiroRedisCache.remove [" + key + "]");
		try {
			V v = get(key);
			redisRepository.delete(this.getRedisKey(key));
			return v;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void clear() throws CacheException {
		LOG.debug("ShiroRedisCache.clear [" + this.keyPattern + "*]");
		try {
			this.redisRepository.clear(this.keyPattern);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public int size() {
		LOG.debug("ShiroRedisCache.size [" + this.keyPattern + "*]");
		try {
			Set<Serializable> keys = this.redisRepository.keys(this.keyPattern);
			return keys == null ? null : keys.size();
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public Set<K> keys() {
		LOG.debug("ShiroRedisCache.keys");
		try {
			Set<Serializable> keys = redisRepository.keys(this.keyPattern);
			if (CollectionUtils.isNotEmpty(keys)) {
				Set<K> ks = new HashSet<K>();
				for (Serializable k : keys)
					ks.add((K) k);
				return ks;
			}
			return Collections.emptySet();
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public Collection<V> values() {
		LOG.debug("ShiroRedisCache.values");
		try {
			Set<Serializable> keys = redisRepository.keys(this.keyPattern);
			if (CollectionUtils.isNotEmpty(keys)) {
				List<V> vs = new ArrayList<V>();
				for (Serializable key : keys) {
					V v = get((K) key);
					if (v != null) {
						vs.add(v);
					}
				}
				return Collections.unmodifiableList(vs);
			}
			return Collections.emptyList();
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

}
