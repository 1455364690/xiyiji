package cn.iselab.mooctest.site.common.aspect;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
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
    @Pointcut("execution(public * cn.iselab.mooctest.site.web.ctrl..*.*(..))")
    public void controllerLog() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.dao..*.*(..))")
    public void daoLog() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.web.logic.impl..*.*(..))")
    public void logicLog() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.service.impl..*.*(..))")
    public void serviceLog() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.web.data..*(..))")
    private void wrapperPointCut() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.web.SessionCounter.*(..))")
    private void sessionCounter() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.web.ctrl.MessageController.*(..)))")
    private void messageController() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.service.impl.MessageServiceImpl.*(..)))")
    private void messageService() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.web.logic.impl.MessageLogicImpl.*(..)))")
    private void messageLogic() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.web.ctrl.ThirdPartToolController.*(..)))")
    private void thirdPartToolController() {
    }

    @Pointcut("execution(public * cn.iselab.mooctest.site.web.logic.impl.ThirdPartToolLogicImpl.*(..)))")
    private void thirdPartToolLogic() {
    }

    //@Before("controllerLog() || logicLog() || serviceLog() || daoLog()")
    public boolean doBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            return false;
        }
        HttpServletRequest request = attributes.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        String[] names = signature.getParameterNames();
        Class[] classes = signature.getParameterTypes();
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        for (int i = 0; i < names.length; i++) {
            if (isString(args[i]) || isPrimitive(args[i]) || classes[i].getName().contains("cn.iselab")) {
                stringJoiner.add(names[i] + ":" + args[i]);
            } else {
                stringJoiner.add(names[i]);
            }
        }
        // 打印请求相关参数
        String userId = "null";
        String sessionId = "null";
        Session session = SecurityUtils.getSubject().getSession(false);
        if (null != session) {
            userId = "" + session.getAttribute("userId");
            sessionId = (String) session.getId();
        }
        // 打印请求 url
        logger.info(String.format("{'sessionId':'%s','userId':'%s','type':'request','url':'%s','httpMethod':'%s','classMethod':'%s.%s','ip':'%s','requestArgs':'%s'}", sessionId, userId, request.getRequestURL().toString(), request.getMethod(), joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), request.getRemoteAddr(), stringJoiner.toString()));
        return true;
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    //@After("controllerLog() || logicLog() || serviceLog() || daoLog()")
    public void doAfter() {
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("(controllerLog() || logicLog() || serviceLog()) && !wrapperPointCut() && !sessionCounter() && !messageController() && !messageLogic() && !thirdPartToolController() && !messageLogic() && !messageService()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        boolean request = doBefore(proceedingJoinPoint);
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            if (request) {
                String userId = "null";
                String sessionId = "null";
                Session session = SecurityUtils.getSubject().getSession(false);
                if (null != session) {
                    userId = "" + session.getAttribute("userId");
                    sessionId = (String) session.getId();
                }
                logger.info(String.format("{'sessionId':'%s','userId':'%s','type':'error','errorMessage':'%s'}", sessionId, userId, throwable.getMessage()));
            }
        }
        // 打印出参
        if (request) {
            String userId = "null";
            String sessionId = "null";
            Session session = SecurityUtils.getSubject().getSession(false);
            if (null != session) {
                userId = "" + session.getAttribute("userId");
                sessionId = (String) session.getId();
            }
            logger.info(String.format("{'sessionId':'%s','userId':'%s','type':'response','responseArgs':'%s','timeConsuming':'%s ms'}", sessionId, userId, result == null ? null : result.toString(), System.currentTimeMillis() - startTime));
        }
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
