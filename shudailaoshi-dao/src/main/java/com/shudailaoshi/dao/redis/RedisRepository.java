package com.shudailaoshi.dao.redis;

import java.io.Serializable;
import java.util.Set;

public interface RedisRepository {

	public void clear(final Serializable pattern);

	public void delete(final Serializable key);

	public void delete(final Serializable... keys);

	public boolean exist(final Serializable key);

	public Object get(final Serializable key);

	public Set<Serializable> keys(final Serializable pattern);

	public void set(final Serializable key, final Object value, Long expireTime);

}
