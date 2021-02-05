package com.vergilyn.examples.mockito.normal;

import com.vergilyn.examples.mockito.AbstractSleepPrint;
import com.vergilyn.examples.mockito.MockitoApplication;
import com.vergilyn.examples.mockito.service.LoginService;
import com.vergilyn.examples.mockito.simulate.TestNotDependencyService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

/**
 *
 * see: <a href="https://segmentfault.com/q/1010000015667498">Spring Boot Test中如何排除一些bean的注入?</a>
 *
 * <br/>
 * 无法在test-unit中指定{@linkplain ComponentScan#basePackages()}，所以暂时不研究这个方案了。<br/>
 *
 * 相对简单的方式可能还是`lazy-init=true`。
 *
 * @author vergilyn
 * @since 2021-02-05
 */
@SpringBootTest(classes = MockitoApplication.class)
/* 无效的 `@ComponentScan` 配置。
 * （即使是启动类）也不能 exclude TestNotDependencyService.class，因为LoginService中存在依赖且lazy-init=false。
 */
@ComponentScan(
		excludeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestNotDependencyService.class)})
public class IncludeSpringbootTest {

	static {
		AbstractSleepPrint.slow_service = 10;
		AbstractSleepPrint.login_service = 10;
		AbstractSleepPrint.startup_slow_run_listener = 10;
		AbstractSleepPrint.test_not_dependency_service = 10;
	}

	@Autowired
	private LoginService loginService;

	@Test
	public void login(){
		String username = "vergilyn";
		String password = "409839163";

		Assertions.assertThrows(NullPointerException.class, () -> {
			String login = loginService.login(username, password);

			System.out.println("test-unit >>>> login: " + login);

		}).printStackTrace();
	}
}
