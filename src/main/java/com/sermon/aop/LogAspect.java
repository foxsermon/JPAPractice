package com.sermon.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	@Pointcut("execution (* com.sermon.service.OrdenManagerImpl.getOrdens() )")
	private void listaOrden() {
	}
	
	@AfterReturning(pointcut = "listaOrden()", returning = "result")
	public void listaAfter(JoinPoint jp, Object result) {
		System.out.print("After ");
		System.out.print(jp.getSignature().getName());
		System.out.println(" result is " + result);
	}
	
	@Around("listaOrden()")
	public Object listaAround(ProceedingJoinPoint pjp) throws Throwable {
		Object retval = null;
		System.out.println("Se cubrira el evento ?");
		try {
			System.out.println(pjp.getSignature().getName());
			for (Object arg : pjp.getArgs()) {
				System.out.println("   " + arg);
			}
			retval = pjp.proceed();
		} catch (Throwable e) {
			System.out.println("Error en el cubre todo. " + e.getMessage());
		}
		System.out.println("Se cubrio todo el evento :) ");
		return retval;
	}
}
