package com.vergilyn.examples.mockito;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 * @author vergilyn
 * @since 2021-02-04
 */
@Slf4j
@SpringBootApplication
@EnableAspectJAutoProxy
//@ComponentScan(
//		excludeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestNotDependencyService.class)})
public class MockitoApplication {
	public static AnnotationConfigServletWebServerApplicationContext _applicationContext;

	public static void main(String[] args) {
		_applicationContext = (AnnotationConfigServletWebServerApplicationContext) SpringApplication.run(MockitoApplication.class, args);
	}
}
