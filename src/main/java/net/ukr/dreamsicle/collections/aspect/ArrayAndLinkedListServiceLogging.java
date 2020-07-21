package net.ukr.dreamsicle.collections.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ArrayAndLinkedListServiceLogging {

    @Around("execution(* net.ukr.dreamsicle.collections.service.ArrayAndLinkedListService.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        log.info(joinPoint.getSignature() + " execute by " + executionTime + "[ms]");
        return proceed;
    }

    @AfterThrowing("execution(* net.ukr.dreamsicle.collections.service.ArrayAndLinkedListService.*(..))")
    public void logAfterThrowingAllMethods() {
        log.error("LoggingAspect.logAfterThrowingAllMethods()");
    }
}
