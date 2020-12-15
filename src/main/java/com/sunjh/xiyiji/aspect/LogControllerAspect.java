package com.sunjh.xiyiji.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import sun.security.provider.MD5;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/11/4 9:36 上午
 */
@Aspect
@Component
@Slf4j
public class LogControllerAspect {
    private final static Logger logger = LoggerFactory.getLogger(LogControllerAspect.class);
    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.sunjh.xiyiji.controller..*.*(..))")
    public void controllerLog() {
    }
    @Pointcut("execution(public * com.sunjh.xiyiji.dao..*.*(..))")
    public void daoLog() {
    }
    @Pointcut("execution(public * com.sunjh.xiyiji.logic..*.*(..))")
    public void logicLog() {
    }
    @Pointcut("execution(public * com.sunjh.xiyiji.service..*.*(..))")
    public void serviceLog() {
    }
    /**
     * 在切点之前织入
     *
     * @param joinPoint
     * @throws Throwable
     */
    //@Before("controllerLog() || logicLog() || serviceLog() || daoLog()")
    public void doBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        String[] names = signature.getParameterNames();
        Class[] classes = signature.getParameterTypes();
        StringJoiner stringJoiner = new StringJoiner(",","[","]");
        for (int i = 0; i < names.length; i++) {
            if(isString(args[i]) || isPrimitive(args[i]) || classes[i].getName().contains("cn.iselab")) {
                stringJoiner.add(names[i] + ":"+ args[i]);
            } else {
                stringJoiner.add(names[i]);
            }
        }
        // 打印请求相关参数
        logger.info("========================================== Start ==========================================");
        // 打印请求 url
        logger.info("[Request]{URL:{} ; HttpMethod:{} ; ClassMethod: {}.{} ; IP:{}} ; RequestArgs:{}", request.getRequestURL().toString(), request.getMethod(), joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),request.getRemoteAddr(),stringJoiner.toString());
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    //@After("controllerLog() || logicLog() || serviceLog() || daoLog()")
    public void doAfter(){
        logger.info("=========================================== End ===========================================");
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    //@Around("controllerLog() || logicLog() || serviceLog() || daoLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        doBefore(proceedingJoinPoint);
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.debug(throwable.getMessage());
        }
        // 打印出参
        logger.info("[Response]{ResponseArgs:{};Time-Consuming : {} ms}", JSON.toJSONString(result),System.currentTimeMillis() - startTime);
        doAfter();
        return result;
    }

    private static boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isString(Object obj) {
        return obj instanceof String;
    }
}
