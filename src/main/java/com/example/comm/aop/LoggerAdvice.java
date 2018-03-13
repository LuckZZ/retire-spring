package com.example.comm.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAdvice {

    private Logger logger = Logger.getLogger(this.getClass());

    @Before("@annotation(loggerManage)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        logger.info("执行 " + loggerManage.description() + " 开始");
        logger.info(joinPoint.getSignature().toString());
    }

    @AfterReturning("@annotation(loggerManage)")
    public void addAfterReturningLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        logger.info("执行 " + loggerManage.description() + " 结束");
    }

    @AfterThrowing(pointcut = "@annotation(loggerManage)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManage loggerManage, Exception ex) {
        logger.error("执行 " + loggerManage.description() + " 异常", ex);
    }

}

