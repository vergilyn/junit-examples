package com.vergilyn.examples.mockito.simulate;

import javax.annotation.PostConstruct;

import com.vergilyn.examples.mockito.AbstractSleepPrint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 由于 spring 默认`lazy-init = false`，会将所有的 bean加载到 application-context。
 * 但是，在test-unit时，大部分的bean是不需要。
 *
 * <p>
 * 解决思路：<br/>
 *   1. 允许的情况下，bean的加载方式改成lazy-init。<br/>
 *   2. test-unit中，指定需要的bean，或 exclude 不需要的bean。<br/>
 * </p>
 * @author vergilyn
 * @since 2021-02-05
 */
@Service(TestNotDependencyService.BEAN_NAME)
@Slf4j
public class TestNotDependencyService extends AbstractSleepPrint {
	public static final String BEAN_NAME = "testNotDependencyService";

	@PostConstruct
	public void init(){
		sleep(test_not_dependency_service);
	}
}
