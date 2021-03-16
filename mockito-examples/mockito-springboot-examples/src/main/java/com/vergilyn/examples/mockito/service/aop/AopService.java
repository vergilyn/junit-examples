package com.vergilyn.examples.mockito.service.aop;

import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

public interface AopService {
	AtomicInteger _AOP_INDEX = new AtomicInteger(0);

	StringJoiner exec(StringJoiner param);
}
