package com.vergilyn.examples.mockito.simulate;

import com.vergilyn.examples.mockito.AbstractSleepPrint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

/**
 * @see org.springframework.boot.context.event.EventPublishingRunListener
 */
@Slf4j
public class StartupSlowRunListener extends AbstractSleepPrint implements SpringApplicationRunListener, Ordered {

	private final SpringApplication application;
	private final String[] args;

	public StartupSlowRunListener(SpringApplication application, String[] args) {
		this.application = application;
		this.args = args;
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
		sleep(startup_slow_run_listener);
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}
