package com.vergilyn.examples.mockito.normal;

import com.vergilyn.examples.mockito.AbstractSleepPrint;
import com.vergilyn.examples.mockito.MockitoApplication;
import com.vergilyn.examples.mockito.service.LoginService;
import com.vergilyn.examples.mockito.simulate.SlowService;
import com.vergilyn.examples.mockito.simulate.TestNotDependencyService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * 通过 lazy，避免不需要的bean提前创建(e.g. {@linkplain TestNotDependencyService})，导致启动过慢。
 *
 * <p>
 *   备注：实际pre环境中可能<b>不允许设置成lazy=true</b>，但开发本地配置成lazy可以便于开发自己写单元测试并验证。
 * </p>
 *
 * @author vergilyn
 * @since 2021-02-05
 *
 * @see NormalSpringbootTest
 */
@SpringBootTest(classes = MockitoApplication.class,
	properties = "spring.main.lazy-initialization=true")
public class LazySpringbootTest {
	static {
		AbstractSleepPrint.slow_service = 10;
		AbstractSleepPrint.login_service = 10;
		AbstractSleepPrint.startup_slow_run_listener = 10;
		AbstractSleepPrint.test_not_dependency_service = 10;
	}

	@Autowired
	private LoginService loginService;
	@Autowired
	private ApplicationContext applicationContext;

	@BeforeEach
	public void before(){
		boolean actual = applicationContext.containsBean(TestNotDependencyService.BEAN_NAME);
		System.out.println("TestNotDependencyService >>>> " + actual);
		// 虽然存在bean，但是并未执行初始化（即@PostConstruct 并未执行）
		Assertions.assertTrue(actual);
	}

	/**
	 * 因为 lazy，所以{@linkplain LoginService#login(String, String)}中：<br/>
	 *   1) 依赖但未使用的 {@linkplain SlowService} 未执行init，节约了10s。<br/>
	 *   2) 未依赖的 {@linkplain TestNotDependencyService} 也未执行init，节约了10s。。<br/>
	 */
	@Test
	public void login(){
		String username = "vergilyn";
		String password = "409839163";

		// login() 内部会调用 RpcService，但是test-unit困难。
		Assertions.assertThrows(NullPointerException.class, () -> {
			String login = loginService.login(username, password);

			System.out.println("test-unit >>>> login: " + login);

		}).printStackTrace();
	}
}
