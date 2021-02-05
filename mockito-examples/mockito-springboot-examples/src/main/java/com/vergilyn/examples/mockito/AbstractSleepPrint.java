package com.vergilyn.examples.mockito;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractSleepPrint {
	public static int startup_slow_run_listener = 0;
	public static int slow_service = 0;
	public static int login_service = 0;
	public static int test_not_dependency_service = 0;

	protected void sleep(long seconds) {
		String name = this.getClass().getSimpleName();

		Duration duration = Duration.ofSeconds(seconds);

		log.warn("[vergilyn] bean[{}] init >>>> sleep begin {}.", name, duration.toString());

		sleepNotThrow(duration);

		log.warn("[vergilyn] bean[{}] init >>>> sleep finish.", name);
	}

	private void sleepNotThrow(Duration duration){
		long seconds = duration.getSeconds();
		if (seconds <= 0){
			return;
		}

		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
		}
	}
}
