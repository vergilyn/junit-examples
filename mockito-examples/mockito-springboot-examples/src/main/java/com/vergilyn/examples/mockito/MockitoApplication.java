package com.vergilyn.examples.mockito;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author vergilyn
 * @since 2021-02-04
 */
@Slf4j
@SpringBootApplication
//@ComponentScan(
//		excludeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestNotDependencyService.class)})
public class MockitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockitoApplication.class, args);
	}
}
