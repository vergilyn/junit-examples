package com.vergilyn.examples.mockito;

import javax.servlet.http.HttpServletRequest;

import com.vergilyn.examples.mockito.controller.LoginController;
import com.vergilyn.examples.mockito.dao.LoginConfigDao;
import com.vergilyn.examples.mockito.dto.LoginDto;
import com.vergilyn.examples.mockito.service.LoginService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 虽然通过 @{@linkplain Mock}, @{@linkplain Spy}, @{@linkplain InjectMocks}
 * 可以完成依赖注入，<b>但是</b>：被测试的方法中，需要用到的所有 bean 都需要手动声明，否则{@linkplain NullPointerException}
 *
 * @author vergilyn
 * @since 2021-02-05
 *
 * @see org.mockito.exceptions.misusing.UnnecessaryStubbingException
 */
/* junit4: `@RunWith(MockitoJUnitRunner.class)`
 * junit5: `@ExtendWith(MockitoExtension.class)`
 *
 * 如果不指定，会导致LoginService无法注入到LoginController，
 * 但LoginConfigDao可以成功注入到LoginService。
 * 猜测：是由于 @InjectMocks 无法注入 @InjectMocks，即使LoginService同时声明了`@Spy`。
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)  // 解决 UnnecessaryStubbingException.class
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MockitoPureJunit5 {
	static {
		AbstractSleepPrint.slow_service = 10;
		AbstractSleepPrint.login_service = 10;
		AbstractSleepPrint.startup_slow_run_listener = 10;
		AbstractSleepPrint.test_not_dependency_service = 10;
	}

	@InjectMocks
	private LoginController loginController;

	// @Mock  // 不会调用 real-method
	@Spy  // 因为该注解，loginController中才能注入loginService
	@InjectMocks  // 因为该注解，才会注入下面mock的LoginConfigDao
	private LoginService loginService;
	@Mock
	private LoginConfigDao loginConfigDao;

	@BeforeEach
	public void before(){
		MockitoAnnotations.openMocks(this);

		Mockito.when(loginConfigDao.getMaxErrorTimes()).thenReturn(0);
	}

	@Test
	@Order(1)
	public void testInject() throws Exception {
		// 通过 @InjectMocks/@Mock/@Spy 注入的依赖

		Object injectLoginService = ReflectionUtils
				.tryToReadFieldValue(LoginController.class, "loginService", loginController)
				.get();

		assertNotNull(injectLoginService);
		assertSame(loginService, injectLoginService);

		Object injectLoginConfigDao = ReflectionUtils
				.tryToReadFieldValue(LoginService.class, "loginConfigDao", loginService)
				.get();

		assertNotNull(injectLoginConfigDao);
		assertSame(loginConfigDao, injectLoginConfigDao);

		// 未注入的依赖
		Object injectAccountService = ReflectionUtils
				.tryToReadFieldValue(LoginService.class, "accountService", loginService)
				.get();

		assertNull(injectAccountService);
	}

	@Test
	@Order(2)
	public void exception() throws Exception {
		// 1. 简单的对象可以直接 new
		LoginDto loginDto = new LoginDto("vergilyn", "409839163");

		// 2. 复杂对象 通过 mock/spy构造
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("v-header")).thenReturn("mockito-header");

		// @Spy 若未指定stud-method，会调用 real-method `loginService#login()`，但是loginService依赖的bean都是NULL!
		NullPointerException throwable = assertThrows(NullPointerException.class, () -> {
			String login = loginController.login(request, loginDto);
			System.out.println(login);
		});

		throwable.printStackTrace();

		// 未注入的依赖
		Object injectAccountService = ReflectionUtils
				.tryToReadFieldValue(LoginService.class, "accountService", loginService)
				.get();

		assertNull(injectAccountService);
	}

	@Test
	@Order(3)
	public void login(){
		LoginDto loginDto = new LoginDto("vergilyn", "409839163");

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("v-header")).thenReturn("mockito-header");

		/* 实际项目中，更多的还是通过：`@Autowired private AccountDao accountDao;` 注入bean，
		 * 并且，不会提供 constructor/setter/getter。
		 * powermock暂时又只支持 `junit 4.x`, `mockito 2.x`。
		 * 所以下面通过reflect设置需要的属性。
		 */



		String login = loginController.login(request, loginDto);
		System.out.println(login);

	}
}
