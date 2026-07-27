package com.suraj.SpringEcom.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    @Pointcut("execution(* com.suraj.SpringEcom.service.ProductService.addProduct(..))"+
    "||execution(* com.suraj.SpringEcom.service.OrderService.placeOrder(..))")
    private void validateInputMethods(){}


    @Before("validateInputMethods()")
    public void validateInputs(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(arg == null){
                LOGGER.error("[VALIDATION] {}.{} -> null argument detected!",
                        joinPoint.getTarget().getClass().getSimpleName(),
                        joinPoint.getSignature().getName());
                throw new IllegalArgumentException("Argument Cannot be null in " +
                        joinPoint.getSignature().getName());
            }
        }
        LOGGER.info("[VALIDATION] {}.{}() -> all inputs are valid",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName());
    }
}
