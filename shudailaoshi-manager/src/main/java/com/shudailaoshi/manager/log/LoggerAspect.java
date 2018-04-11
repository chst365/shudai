package com.shudailaoshi.manager.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shudailaoshi.entity.sys.Operating;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.pojo.exceptions.ControllerException;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.service.sys.OperatingService;
import com.shudailaoshi.utils.IPUtil;
import com.shudailaoshi.utils.SessionUtil;

/**
 * 操作日志
 * @author
 */
@Aspect
@Component
public class LoggerAspect {

    private static final Logger LOG = LogManager.getLogger(LoggerAspect.class);

    private static final Level SERVICE = Level.forName("SERVICE", 301);
    private static final Level CONTROLLER = Level.forName("CONTROLLER", 399);

    private static final String CLASS_LOG = "[CLASS]: ";
    private static final String METHOD_LOG = "; [METHOD]: ";
    private static final String TIME_LOG = "; [TIME]: ";
    private static final String IP_LOG = "; [IP]: ";
    private static final String USER_LOG = "; [USER]: ";
    private static final String EXCEPTION_LOG = "; [EXCEPTION]: ";

    @Autowired
    private OperatingService operatingService;

    @Pointcut("execution(* com.shudailaoshi.service.*.impl.*.*(..))")
    public void serviceExceptionLog() {
    }

    @After(value = "execution(* com.shudailaoshi.web.controller..*(..)) && @annotation(comment)")
    public void controllerDoAfter(JoinPoint joinPoint, Comment comment) {
        try {
            Operating operating = new Operating();
            operating.setLogText(comment.value());
            User user = (User) SessionUtil.getSessionAttribute(Constant.CURRENT_USER);
            String userName = null;
            Long userId = null;
            if (user != null) {
                userName = user.getUserName();
                userId = user.getId();
            }
            operating.setUserId(userId);
            operating.setUserName(userName);
            operating.setClazzName(joinPoint.getTarget().getClass().getName());
            operating.setMethodName(joinPoint.getSignature().getName());
            operating.setIpAddress(IPUtil.getIP(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()));
            this.operatingService.saveOperating(operating);
        } catch (Throwable e) {
            LOG.info(e.getMessage(), e);
        }
    }

    @Around(value = "execution(* com.shudailaoshi.web.controller..*(..))")
    public Object controllerDoAround(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            long begin = System.currentTimeMillis();
            Object object = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            StringBuilder msg = new StringBuilder(CLASS_LOG);
            User user = (User) SessionUtil.getSessionAttribute(Constant.CURRENT_USER);
            String userName = user == null ? null : user.getUserName();
            msg.append(proceedingJoinPoint.getTarget().getClass().getName()).append(METHOD_LOG)
                    .append(proceedingJoinPoint.getSignature().getName()).append(TIME_LOG).append(end - begin)
                    .append("ms").append(IP_LOG)
                    .append(IPUtil.getIP(
                            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()))
                    .append(USER_LOG).append(userName);
            LOG.log(CONTROLLER, msg.toString());
            return object;
        } catch (Throwable e) {
            LOG.info(e.getMessage(), e);
            throw new ControllerException(ExceptionEnum.UNKNOWN);
        }
    }

    @AfterThrowing(pointcut = "serviceExceptionLog()", throwing = "e")
    public void serviceDoAfterThrowing(JoinPoint joinPoint, Throwable e) {
        StringBuilder msg = new StringBuilder(CLASS_LOG);
        User user = (User) SessionUtil.getSessionAttribute(Constant.CURRENT_USER);
        String userName = user == null ? null : user.getUserName();
        msg.append(joinPoint.getTarget().getClass().getName()).append(METHOD_LOG)
                .append(joinPoint.getSignature().getName()).append(IP_LOG)
                .append(IPUtil.getIP(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()))
                .append(USER_LOG).append(userName).append(EXCEPTION_LOG).append(e.getMessage());
        LOG.log(SERVICE, msg.toString());
    }

}
