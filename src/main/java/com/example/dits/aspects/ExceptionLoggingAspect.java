package com.example.dits.aspects;

import com.example.dits.loggers.ExceptionLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
public class ExceptionLoggingAspect {
    private ExceptionLogger exceptionLogger;

    @Autowired
    public ExceptionLoggingAspect(ExceptionLogger exceptionLogger) {
        this.exceptionLogger = exceptionLogger;
    }

    @AfterThrowing(value = "execution(* com.example.dits.controllers.*.*(..))", throwing = "e")
    public void logAfterThrowingNotFoundException(JoinPoint joinPoint, Exception e) {
        String message = "exception " + e.getClass().getSimpleName() + "was thrown in "
                + joinPoint.getSignature().getName()
                + "\n\t\t" + e.getMessage();
        exceptionLogger.log(message, new Date());
    }
}