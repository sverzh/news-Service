package com.github.sverzh.newsService.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Around("@annotation(Loggable)")
    public Object loggable(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("run method : {} , args {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
        Object proceed = joinPoint.proceed();
        log.info("method result : {}", proceed);
        return proceed;
    }

    @AfterThrowing(value = "@annotation(Loggable)", throwing = "exception")
    public void loggableException(Exception exception) {
        log.error("Method resulted into exception, message: {}", exception.getMessage());
    }
}