package com.vergilyn.examples.mockito.normal;

import com.vergilyn.examples.mockito.AbstractSleepPrint;
import com.vergilyn.examples.mockito.MockitoApplication;
import com.vergilyn.examples.mockito.rpc.RpcService;
import com.vergilyn.examples.mockito.service.LoginService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 默认会完整的加载spring-boot，导致启动很慢<br/>
 *
 * 当测试{@linkplain LoginService#login(String, String)}时，
 * 由于依赖了{@linkplain RpcService}外部服务，测试起来很麻烦。
 *
 * @author vergilyn
 * @since 2021-02-05
 *
 * @see LazySpringbootTest
 */
@SpringBootTest(classes = MockitoApplication.class)
public class NormalSpringbootTest {
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

		// login() 内部会调用 RpcService，但是test-unit困难。
		Assertions.assertThrows(NullPointerException.class, () -> {
			String login = loginService.login(username, password);

			System.out.println("test-unit >>>> login: " + login);

		}).printStackTrace();
	}
}
