package net.ukr.dreamsicle.collections.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class RepositoryAspectLogging {

    @Before("execution(* net.ukr.dreamsicle.collections.repository.*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("repository method call {}", joinPoint.getSignature().getName());
    }
}