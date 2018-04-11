package com.shudailaoshi.manager.cache.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
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
@Component
public class ShiroRedisSessionDao extends AbstractSessionDAO {

	private static final Logger LOG = LoggerFactory.getLogger(ShiroRedisSessionDao.class);

	@Autowired
	private RedisRepository redisRepository;
	@Value("${redis.defaultExpireTime}")
	private long defaultExpireTime;

	private String keyPrefix = "shiro_session:";

	private String getRedisKey(Serializable sessionId) {
		return new StringBuffer(this.keyPrefix).append(sessionId).toString();
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}

	/**
	 * save session
	 * 
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException {
		LOG.debug("ShiroRedisSessionDao.saveSession");
		if (session == null || session.getId() == null) {
			LOG.error("session or session id is null");
			return;
		}
		Long expireTime = this.defaultExpireTime;
		session.setTimeout(expireTime * 1000);
		this.redisRepository.set(this.getRedisKey(session.getId()), session, expireTime);
	}

	@Override
	public void delete(Session session) {
		LOG.debug("ShiroRedisSessionDao.delete");
		if (session == null || session.getId() == null) {
			LOG.error("session or session id is null");
			return;
		}
		this.redisRepository.delete(this.getRedisKey(session.getId()));
	}

	@Override
	public Collection<Session> getActiveSessions() {
		LOG.debug("ShiroRedisSessionDao.getActiveSessions");
		Set<Serializable> keys = this.redisRepository.keys(this.keyPrefix + "*");
		if (CollectionUtils.isNotEmpty(keys)) {
			Set<Session> sessions = new HashSet<Session>();
			for (Serializable key : keys) {
				Session session = (Session) this.redisRepository.get(key);
				sessions.add(session);
			}
			return sessions;
		}
		return Collections.emptySet();
	}

	@Override
	protected Serializable doCreate(Session session) {
		LOG.debug("ShiroRedisSessionDao.doCreate");
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		LOG.debug("ShiroRedisSessionDao.doReadSession");
		if (sessionId == null) {
			LOG.error("session id is null");
			return null;
		}
		Session session = (Session) this.redisRepository.get(this.getRedisKey(sessionId));
		return session;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

}
