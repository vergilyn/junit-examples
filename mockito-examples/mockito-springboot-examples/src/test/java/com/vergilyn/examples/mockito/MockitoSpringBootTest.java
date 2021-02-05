package com.vergilyn.examples.mockito;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import com.vergilyn.examples.mockito.controller.LoginController;
import com.vergilyn.examples.mockito.dao.LoginConfigDao;
import com.vergilyn.examples.mockito.dto.LoginDto;
import com.vergilyn.examples.mockito.rpc.RpcService;
import com.vergilyn.examples.mockito.service.LoginService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.util.ReflectionUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

/**
 * 如果`lazy-init=false`依然回到了之前的问题，spring-boot启动慢。
 *
 * @author vergilyn
 * @since 2021-02-04
 *
 * @see <a href="https://www.cnblogs.com/zhuang229/p/12237269.html">单元测试中简单使用Mockito解决Spring Bean依赖树问题</a>
 */
@SpringBootTest(classes = MockitoApplication.class,
		properties = "spring.main.lazy-initialization=true")
public class MockitoSpringBootTest {

	static {
		AbstractSleepPrint.slow_service = 1;
		AbstractSleepPrint.login_service = 1;
		AbstractSleepPrint.startup_slow_run_listener = 1;
		AbstractSleepPrint.test_not_dependency_service = 1;
	}

	/**
	 * 使用{@linkplain org.springframework.boot.test.mock.mockito.SpyBean}注解，
	 * 将 LoginController 模拟为 spy-bean，它将在spring上下文初始化时就替换掉原有Bean。
	 */
	@SpyBean
	private LoginController loginController;
	@SpyBean
	private LoginService loginService;
	@MockBean
	private LoginConfigDao loginConfigDao;

	@BeforeEach
	public void before(){
		MockitoAnnotations.openMocks(this);

		Mockito.when(loginConfigDao.getMaxErrorTimes()).thenReturn(2);
	}

	@Test
	public void login() throws IllegalAccessException {
		String username = "vergilyn";
		String password = "409839163";
		// 1. 简单的对象可以直接 new
		LoginDto loginDto = new LoginDto(username, password);

		// 2. 复杂对象 通过 mock/spy构造
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader("v-header")).thenReturn("mockito-header");

		RpcService mockRpcService = Mockito.mock(RpcService.class);
		Mockito.when(mockRpcService.doSomething(anyString())).thenReturn("mockito-rpc");
		// powermock暂时只支持 `junit 4.x`, `mockito 2.x`。
		// 所以通过 reflect 设置 RpcService;
		Field fieldRpcService = ReflectionUtils.findField(LoginService.class, "rpcService");
		fieldRpcService.setAccessible(true);
		fieldRpcService.set(loginService, mockRpcService);

		// Mockito.when(loginService.login(anyString(), anyString())).thenReturn("mockito-login");

		String login = loginController.login(request, loginDto);
		System.out.println(login);

		// 验证调用顺序
		InOrder inOrder = Mockito.inOrder(loginController, request, loginService, mockRpcService);
		inOrder.verify(loginController, times(1))
				.login(any(HttpServletRequest.class), eq(loginDto));
		inOrder.verify(loginService, times(1))
				.login(username, password);
		inOrder.verify(mockRpcService, times(1))
				.doSomething(username);
		inOrder.verify(request, times(1))
				.getHeader("v-header");

		Assertions.assertEquals("true,mockito-header", login);
	}
}
