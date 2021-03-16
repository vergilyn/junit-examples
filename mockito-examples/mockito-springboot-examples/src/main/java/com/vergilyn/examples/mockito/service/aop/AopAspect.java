package com.vergilyn.examples.mockito.service.aop;

import java.util.StringJoiner;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.vergilyn.examples.mockito.service.aop.AopService._AOP_INDEX;

@Aspect
@Slf4j
@Component
public class AopAspect {

	@Around(value = "execution(* AopMainServiceImpl.exec(..)) || execution(* AopSubServiceImpl.exec(..))")
	public Object handle(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();

		StringJoiner tmp = (StringJoiner) args[0];

		args[0] = tmp.add("-(aop-proceed-before)");

		log.info("[{}]aop-PROCEED before >>>> args[0]: {}, before: {}", _AOP_INDEX.incrementAndGet(), tmp, args[0]);

		StringJoiner result = (StringJoiner) pjp.proceed(args);

		tmp = result;
		result.add("-(aop-proceed-after)");

		log.info("[{}]aop-PROCEED before >>>> result: {}, after: {}", _AOP_INDEX.incrementAndGet(), tmp, result);

		return result;
	}
}
