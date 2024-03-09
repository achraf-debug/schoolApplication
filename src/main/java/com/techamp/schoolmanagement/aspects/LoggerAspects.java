package com.techamp.schoolmanagement.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggerAspects {
	
	@Around(value = "execution(* com.techamp.schoolmanagement..*.*(..))")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		String method = joinPoint.getSignature().toString();
		log.info("************************************** ");
		log.info(method + " method execution start");
		Instant start = Instant.now();
		Object returnObj = joinPoint.proceed();
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		log.info("Time took execution method : " + method + " Time (ms) : " + timeElapsed);
		log.info(method + " method execution end");
		log.info("************************************** ");
		return returnObj;
	}
	
	@AfterThrowing(value = "execution(* com.techamp.schoolmanagement..*.*(..))", throwing = "ex")
	public void logException(JoinPoint joinPoint, Exception ex) {
		log.info("--------------------- EXCEPTION ---------------------- ");
		log.error(joinPoint.getSignature().toString() + " An exception occured due to : " + ex.getMessage());
	}
}
