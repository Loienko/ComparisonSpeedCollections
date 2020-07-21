package net.ukr.dreamsicle.collections.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class SetAndMapCollectionsServiceLogging {

    @Before("execution(* net.ukr.dreamsicle.collections.service.SetAndMapService.*(..))")
    public void beforeRunning(JoinPoint joinPoint) {
        log.info("Getting started method {{}", joinPoint.getSignature());
    }

    @After("execution(* net.ukr.dreamsicle.collections.service.SetAndMapService.*(..))")
    public void afterRunning(JoinPoint joinPoint) {
        log.info("End of work method {}", joinPoint.getSignature());
    }
}
