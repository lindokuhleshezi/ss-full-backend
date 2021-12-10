package za.ac.ss.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class LoggingService {
	
	@Around("execution(* za.ac.ss.service..*(..)))")
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		final StopWatch stopWatch = new StopWatch();
		// Measure method execution time
		stopWatch.start();
		
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		String username = authentication.getName();
		
		// Object principal = authentication.getPrincipal();
		// Get intercepted method details
		String className = methodSignature.getDeclaringTypeName();
		String[] param = methodSignature.getParameterNames();

		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		// Log method execution time
		log.info("username: {} - {} ({}) has executed in {} ms", username, className, param, stopWatch.getTotalTimeMillis());
		
		return result;
	}

}
