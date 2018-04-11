package com.shudailaoshi.dao.redis;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import com.shudailaoshi.pojo.vos.common.MsgVO;

public final class RedisPubRepository {

	private static final Logger LOG = LogManager.getLogger(RedisRepository.class);

	private RedisTemplate<Serializable, Object> redisPubTemplate;

	private static final String EXPIRE_KEY = "msg_expire:";
	private static final String SPLIT = ":";

	public boolean saveMsg(MsgVO vo, Long minutes) {
		boolean result = false;
		try {
			String key = new StringBuilder(EXPIRE_KEY).append(vo.getParam()).append(SPLIT).append(vo.getType())
					.toString();
			this.redisPubTemplate.opsForValue().set(key, vo);
			this.redisPubTemplate.expire(key, minutes, TimeUnit.MINUTES);
			result = true;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public RedisTemplate<Serializable, Object> getRedisPubTemplate() {
		return redisPubTemplate;
	}

	public void setRedisPubTemplate(RedisTemplate<Serializable, Object> redisPubTemplate) {
		this.redisPubTemplate = redisPubTemplate;
	}

}
