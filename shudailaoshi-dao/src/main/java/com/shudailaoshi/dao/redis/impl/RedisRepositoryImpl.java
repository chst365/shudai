package com.shudailaoshi.dao.redis.impl;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.shudailaoshi.dao.redis.RedisRepository;

@Component
public final class RedisRepositoryImpl implements RedisRepository {

	private static final Logger LOG = LogManager.getLogger(RedisRepositoryImpl.class);

	@Autowired
	private RedisTemplate<Serializable, Object> redisTemplate;

	@Value("${redis.defaultExpireTime}")
	private long defaultExpireTime;

	public void clear(final Serializable pattern) {
		LOG.debug("RedisRepositoryImpl.clear");
		Set<Serializable> keys = this.keys(pattern);
		for (Serializable key : keys)
			this.delete(key);
	}

	public void delete(final Serializable key) {
		LOG.debug("RedisRepositoryImpl.delete key");
		if (exist(key))
			this.redisTemplate.delete(key);
	}

	public void delete(final Serializable... keys) {
		LOG.debug("RedisRepositoryImpl.delete keys");
		for (Serializable key : keys)
			this.delete(key);
	}

	public boolean exist(final Serializable key) {
		LOG.debug("RedisRepositoryImpl.exist");
		return this.redisTemplate.hasKey(key);
	}

	public Object get(final Serializable key) {
		LOG.debug("RedisRepositoryImpl.get");
		return this.redisTemplate.opsForValue().get(key);
	}

	public Set<Serializable> keys(final Serializable pattern) {
		LOG.debug("RedisRepositoryImpl.keys");
		return this.redisTemplate.keys(pattern);
	}

	public void set(final Serializable key, final Object value, Long expireTime) {
		LOG.debug("RedisRepositoryImpl.set");
		this.redisTemplate.opsForValue().set(key, value);
		LOG.debug("RedisRepositoryImpl.expire");
		if (expireTime == null) {
			this.redisTemplate.expire(key, this.defaultExpireTime, TimeUnit.SECONDS);
			return;
		}
		this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
	}

}
