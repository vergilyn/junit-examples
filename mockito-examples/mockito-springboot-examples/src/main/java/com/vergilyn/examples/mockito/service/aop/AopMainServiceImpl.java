package com.vergilyn.examples.mockito.service.aop;

import java.util.StringJoiner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("aopMainServiceImpl")
public class AopMainServiceImpl implements AopService{
	@Autowired
	public AopSubServiceImpl sub;

	@Override
	public StringJoiner exec(StringJoiner param){
		log.info("[{}]aop-MAIN exec >>>> param: {}", _AOP_INDEX.incrementAndGet(), param);

		param.add("-(main-before-sub)");

		StringJoiner subRs = sub.exec(param);
		log.info("[{}]aop-MAIN invoke `SUB.exec({})` >>>> rs: {}", _AOP_INDEX.incrementAndGet(), param, subRs);

		return param.add("-(main-after-sub)");
	}
}
