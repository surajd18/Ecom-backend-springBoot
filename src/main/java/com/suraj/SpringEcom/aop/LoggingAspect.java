package com.suraj.SpringEcom.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    //return type,class-name.method-name(args)

    @Pointcut("execution(* com.suraj.SpringEcom.service.ProductService.*(..)" +
    "|| execution(* com.suraj.SpringEcom.service.OrderService.*(..)")

    public void serviceLayer(){}

    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint){
        LOGGER.info("[START] {}.{}()-> args:{}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "serviceLayer()",returning = "result")
    public void logAfterReturing(JoinPoint joinPoint,Object result){
        LOGGER.info("[SUCCESS] {}.{} -> returned: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                result);
    }

    @AfterThrowing(pointcut = "serviceLayer()",throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint,Throwable ex){
        LOGGER.error("[ERROR] {}.{} -> threw: {} | message: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }
}
