package com.snapppay.expensetracker.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogControllerAspect {

    @Pointcut("execution(public * com.snapppay.expensetracker.controller.*.*(..))")
    private void callApiLogging() {
    }

    @Around(value = "callApiLogging()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        var request = new StringBuilder();
        request.append("methodName: ").append(joinPoint.getSignature().getName()).append("\n");
        Arrays.stream(args).forEach(arg -> {
            request.append(arg.toString()).append("\n");
        });
        log.info(request.toString());
        Object response = joinPoint.proceed();
        log.info(response.toString());
        return response;
    }
}
