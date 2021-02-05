package com.vergilyn.examples.mockito.simulate;

import javax.annotation.PostConstruct;

import com.vergilyn.examples.mockito.AbstractSleepPrint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 模拟因为 bean 导致spring-boot启动缓慢。
 *
 * @author vergilyn
 * @since 2021-02-05
 */
@Service
@Slf4j
public class SlowService extends AbstractSleepPrint {

	@PostConstruct
	public void init(){
		sleep(slow_service);
	}
}
