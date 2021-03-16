package com.vergilyn.examples.mockito.service.aop;

import java.util.StringJoiner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("aopSubServiceImpl")
public class AopSubServiceImpl implements AopService {

	@Override
	public StringJoiner exec(StringJoiner param) {
		log.info("[{}]aop-SUB exec >>>> param: {}", _AOP_INDEX.incrementAndGet(), param);

		return param.add("-(sub-exec)");
	}
}
