//package com.shudailaoshi.dao.redis.impl;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import com.shudailaoshi.dao.redis.ShardedRedisProxy;
//
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPool;
//
///**
// * 
// * 本实现为分片Redis缓存服务代理
// * 
// * @author eko
// */
//public class ShardedRedisProxyImpl implements ShardedRedisProxy {
//
//	private static final Logger LOG = LogManager.getLogger(ShardedRedisProxyImpl.class);
//
//	/**
//	 * 缓存连接池
//	 */
//	@Autowired
//	private ShardedJedisPool shardedJedisPool;
//	@Autowired
//	private StringRedisSerializer stringRedisSerializer;
//	@Autowired
//	private JdkSerializationRedisSerializer jdkSerializationRedisSerializer;
//
//	@Override
//	public Long append(String key, Object value) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			return shardedJedis.append(this.stringRedisSerializer.serialize(key),
//					this.jdkSerializationRedisSerializer.serialize(value));
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return null;
//	}
//
//	@Override
//	public Long decr(String key) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			return shardedJedis.decr(this.stringRedisSerializer.serialize(key));
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return null;
//	}
//
//	@Override
//	public Long decrBy(String key, long integer) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			return shardedJedis.decrBy(this.stringRedisSerializer.serialize(key), integer);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return null;
//	}
//
//	@Override
//	public Long del(String key) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.del(this.stringRedisSerializer.serialize(key));
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Boolean exists(String key) {
//		ShardedJedis shardedJedis = null;
//		Boolean ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.exists(this.stringRedisSerializer.serialize(key));
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Long expire(String key, int seconds) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.expire(this.stringRedisSerializer.serialize(key), seconds);
//			return ret;
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Long expireAt(String key, long unixTime) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.expireAt(this.stringRedisSerializer.serialize(key), unixTime);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Object get(String key) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			return jdkSerializationRedisSerializer
//					.deserialize(shardedJedis.get(this.stringRedisSerializer.serialize(key)));
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return null;
//	}
//
//	@Override
//	public byte[] getSet(String key, Object value) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			return shardedJedis.getSet(this.stringRedisSerializer.serialize(key),
//					this.jdkSerializationRedisSerializer.serialize(value));
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return null;
//	}
//
//	@Override
//	public Object getrange(String key, long startOffset, long endOffset) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			return this.jdkSerializationRedisSerializer.deserialize(
//					shardedJedis.getrange(this.stringRedisSerializer.serialize(key), startOffset, endOffset));
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return null;
//	}
//
//	@Override
//	public Long hdel(String key, String field) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hdel(key, field);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Boolean hexists(String key, String field) {
//		ShardedJedis shardedJedis = null;
//		Boolean ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hexists(key, field);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public String hget(String key, String field) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hget(key, field);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Map<String, String> hgetAll(String key) {
//		ShardedJedis shardedJedis = null;
//		Map<String, String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hgetAll(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Long hincrBy(String key, String field, long value) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hincrBy(key, field, value);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Set<String> hkeys(String key) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			Set<byte[]> bytes = shardedJedis.hkeys(this.stringRedisSerializer.serialize(key));
//			Set<String> values = new HashSet<>();
//			for (byte[] object : bytes) {
//				values.add(this.stringRedisSerializer.deserialize(object));
//			}
//			return values;
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return null;
//	}
//
//	@Override
//	public Long hlen(String key) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hlen(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public List<String> hmget(String key, String... fields) {
//		ShardedJedis shardedJedis = null;
//		List<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hmget(key, fields);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public String hmset(String key, Map<String, String> hash) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hmset(key, hash);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Long hset(String key, String field, String value) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hset(key, field, value);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Long hsetnx(String key, String field, String value) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hsetnx(key, field, value);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public List<String> hvals(String key) {
//		ShardedJedis shardedJedis = null;
//		List<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.hvals(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long incr(String key) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.incr(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long incrBy(String key, long integer) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.incrBy(key, integer);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public String lindex(String key, long index) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.lindex(key, index);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long llen(String key) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.llen(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public String lpop(String key) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.lpop(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Long lpush(String key, String string) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.lpush(key, string);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public List<String> lrange(String key, long start, long end) {
//		ShardedJedis shardedJedis = null;
//		List<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.lrange(key, start, end);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long lrem(String key, long count, String value) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.lrem(key, count, value);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public String lset(String key, long index, String value) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.lset(key, index, value);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public String ltrim(String key, long start, long end) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.ltrim(key, start, end);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public String rpop(String key) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.rpop(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long rpush(String key, String string) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.rpush(key, string);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public Long sadd(String key, String member) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.sadd(key, member);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long scard(String key) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.scard(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	@Override
//	public String set(String key, Object value) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			return shardedJedis.set(this.stringRedisSerializer.serialize(key),
//					this.jdkSerializationRedisSerializer.serialize(value));
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return null;
//	}
//
//	public String setex(String key, int seconds, String value) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.setex(key, seconds, value);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long setnx(String key, String value) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.setnx(key, value);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public long setrange(String key, long offset, String value) {
//		ShardedJedis shardedJedis = null;
//		long ret = 0;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.setrange(key, offset, value);
//			return ret;
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Boolean sismember(String key, String member) {
//		ShardedJedis shardedJedis = null;
//		Boolean ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.sismember(key, member);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Set<String> smembers(String key) {
//		ShardedJedis shardedJedis = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			Set<String> ret = shardedJedis.smembers(key);
//			return ret;
//		} catch (Exception e) {
//			LOG.error(this, e);
//			return null;
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//	}
//
//	public List<String> sort(String key) {
//		ShardedJedis shardedJedis = null;
//		List<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.sort(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public String spop(String key) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.spop(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public String srandmember(String key) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.srandmember(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long srem(String key, String member) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.srem(key, member);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public String substr(String key, int start, int end) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.substr(key, start, end);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long ttl(String key) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.ttl(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public String type(String key) {
//		ShardedJedis shardedJedis = null;
//		String ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.type(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long zadd(String key, double score, String member) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zadd(key, score, member);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long zcard(String key) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zcard(key);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long zcount(String key, double min, double max) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zcount(key, min, max);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Double zincrby(String key, double score, String member) {
//		ShardedJedis shardedJedis = null;
//		Double ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zincrby(key, score, member);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Set<String> zrange(String key, int start, int end) {
//		ShardedJedis shardedJedis = null;
//		Set<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zrange(key, start, end);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Set<String> zrangeByScore(String key, double min, double max) {
//		ShardedJedis shardedJedis = null;
//		Set<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zrangeByScore(key, min, max);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
//		ShardedJedis shardedJedis = null;
//		Set<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zrangeByScore(key, min, max, offset, count);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long zrank(String key, String member) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zrank(key, member);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long zrem(String key, String member) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zrem(key, member);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long zremrangeByRank(String key, int start, int end) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zremrangeByRank(key, start, end);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long zremrangeByScore(String key, double start, double end) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zremrangeByScore(key, start, end);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Set<String> zrevrange(String key, int start, int end) {
//		ShardedJedis shardedJedis = null;
//		Set<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zrevrange(key, start, end);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Set<String> zrevrangeByScore(String key, double max, double min) {
//		ShardedJedis shardedJedis = null;
//		Set<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zrevrangeByScore(key, max, min);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
//		ShardedJedis shardedJedis = null;
//		Set<String> ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zrevrangeByScore(key, max, min, offset, count);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Long zrevrank(String key, String member) {
//		ShardedJedis shardedJedis = null;
//		Long ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zrevrank(key, member);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	public Double zscore(String key, String member) {
//		ShardedJedis shardedJedis = null;
//		Double ret = null;
//		try {
//			shardedJedis = shardedJedisPool.getResource();
//			ret = shardedJedis.zscore(key, member);
//		} catch (Exception e) {
//			LOG.error(this, e);
//		} finally {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//		return ret;
//	}
//
//	/**
//	 * @return the shardedJedisPool
//	 */
//	public ShardedJedisPool getShardedJedisPool() {
//		return shardedJedisPool;
//	}
//
//	/**
//	 * @param shardedJedisPool
//	 *            the shardedJedisPool to set
//	 */
//	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
//		this.shardedJedisPool = shardedJedisPool;
//	}
//
//}