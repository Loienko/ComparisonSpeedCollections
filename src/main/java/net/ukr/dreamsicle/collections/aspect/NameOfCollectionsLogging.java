package net.ukr.dreamsicle.collections.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class NameOfCollectionsLogging {

    @AfterReturning("execution(* net.ukr.dreamsicle.collections.service.NameOfCollectionService.*(..))")
    public void logAfterReturning(JoinPoint joinPoint) {
        log.info("log NameOfCollectionService after method call {}", joinPoint.getSignature().getName());
    }
}
