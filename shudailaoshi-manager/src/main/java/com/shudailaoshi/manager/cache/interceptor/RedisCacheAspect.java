package com.shudailaoshi.manager.cache.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shudailaoshi.dao.redis.RedisRepository;
import com.shudailaoshi.manager.redis.RedisManager;
import com.shudailaoshi.pojo.annotation.RedisCacheMethod;
import com.shudailaoshi.utils.PropertiesUtil;
import com.shudailaoshi.utils.ReflectionUtil;
import com.shudailaoshi.utils.ValidUtil;

/**
 * 
 * @author liaoyifan
 *
 */
@Aspect
@Component
public class RedisCacheAspect {

	private static final Logger LOG = LoggerFactory.getLogger(RedisCacheAspect.class);

	@Autowired
	private RedisManager redisManager;
	@Autowired
	private RedisRepository redisRepository;// redis仓库
	@Value("${redis.defaultExpireTime}")
	private long defaultExpireTime;

	private static Set<String> CACHE_SIMPLE_CLASS_METHOD_NAMES = new HashSet<String>(); // 缓存方法名称

	static {
		String str = PropertiesUtil.getConfigProperty("cache.simple.class.method.names");
		if (ValidUtil.isNotBlank(str)) {
			CACHE_SIMPLE_CLASS_METHOD_NAMES = new HashSet<String>(Arrays.asList(str.split(",")));
		}
	}

	@Pointcut("@annotation(com.shudailaoshi.pojo.annotation.RedisCacheMethod)")
	public void redisCacheUpdate() {
	}

	@Pointcut("execution(* com.shudailaoshi.service.*.impl.*.*(..))")
	public void redisCacheSave() {
	}

	@After("redisCacheUpdate()")
	public void redisCacheUpdateDoAfter(JoinPoint joinPoint) {
		try {
			Map<String, Object> values = getValues(joinPoint);
			ReflectionUtil.invokeMethod(this.redisManager, (String) values.get("methodName"),
					(Class<?>[]) values.get("parameterTypes"), (Object[]) values.get("parameters"));
		} catch (Exception e) {
			LOG.info(e.getMessage(), e);
		}
	}

	@Around("redisCacheSave()")
	public Object redisCacheSaveDoAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String simpleClassName = proceedingJoinPoint.getTarget().getClass().getSimpleName();
		String methodName = proceedingJoinPoint.getSignature().getName();
		// 判断是否需要缓存
		if (CACHE_SIMPLE_CLASS_METHOD_NAMES.contains((simpleClassName + "." + methodName))) {
			try {
				Object[] arguments = proceedingJoinPoint.getArgs();
				String key = getCacheKey(simpleClassName, methodName, arguments);
				// 判断redis是否存在缓存
				if (redisRepository.exist(key)) {
					return redisRepository.get(key);
				}
				Object proceed = proceedingJoinPoint.proceed();
				// 写入缓存
				if (proceed != null) {
					final String tkey = key;
					final Object tvalue = proceed;
					new Thread(new Runnable() {
						@Override
						public void run() {
							redisRepository.set(tkey, tvalue, defaultExpireTime);
						}
					}).start();
				}
			} catch (Exception e) {
				LOG.info(e.getMessage(), e);
			}
		}
		return proceedingJoinPoint.proceed();
	}

	private static Map<String, Object> getValues(JoinPoint joinPoint) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Method[] methods = Class.forName(className).getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class<?>[] paramTypes = method.getParameterTypes();
				int count = paramTypes.length;
				if (count == arguments.length) {
					List<String> paramNames = ReflectionUtil.getParamNames(method);
					Map<String, Map<String, Object>> paramMap = new HashMap<String, Map<String, Object>>();
					for (int i = 0; i < count; i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("type", paramTypes[i]);
						map.put("value", arguments[i]);
						paramMap.put(paramNames.get(i), map);
					}
					Object[] parameters = null;
					Class<?>[] parameterTypes = null;
					if (!paramMap.isEmpty()) {
						String[] annoParamNames = method.getAnnotation(RedisCacheMethod.class).paramNames();
						int size = annoParamNames.length;
						if (size > 0) {
							parameters = new Object[size];
							parameterTypes = new Class<?>[size];
							for (int i = 0; i < size; i++) {
								Map<String, Object> param = paramMap.get(annoParamNames[i]);
								parameterTypes[i] = (Class<?>) param.get("type");
								parameters[i] = param.get("value");
							}
						}
					}
					result.put("methodName", method.getAnnotation(RedisCacheMethod.class).methodName());
					result.put("parameters", parameters);
					result.put("parameterTypes", parameterTypes);
					break;
				}
			}
		}
		return result;
	}

	private String getCacheKey(String simpleClassName, String methodName, Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(simpleClassName).append("_").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (Object ags : arguments) {
				sb.append("_").append(ags);
			}
		}
		return sb.toString();
	}

}
