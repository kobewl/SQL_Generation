package com.sql.generation.aop;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.MDC;
import com.alibaba.fastjson.JSON;

/**
 * 请求响应日志 AOP
 *
 * @author wangliang
 **/
@Aspect
@Component
@Slf4j
public class LogInterceptor {

    /**
     * 执行拦截
     */
    @Around("execution(* com.sql.generation.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        // 添加MDC支持
        MDC.put("requestId", UUID.randomUUID().toString());
        // 记录更多信息
        log.info("Request start: method={}, args={}", 
            point.getSignature().getName(),
            JSON.toJSONString(point.getArgs()));
        try {
            Object result = point.proceed();
            log.info("Request success: result={}", JSON.toJSONString(result));
            return result;
        } catch (Throwable e) {
            log.error("Request error", e);
            throw e;
        } finally {
            MDC.clear();
        }
    }
}

