package com.vergilyn.examples.mockito.aop;

import java.util.StringJoiner;

import com.vergilyn.examples.mockito.MockitoApplication;
import com.vergilyn.examples.mockito.service.aop.AopMainServiceImpl;
import com.vergilyn.examples.mockito.service.aop.AopSubServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.util.AopTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import static com.vergilyn.examples.mockito.service.aop.AopService._AOP_INDEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoAopTests {

	@Nested
	@SpringBootTest(classes = MockitoApplication.class,
			properties = "spring.main.lazy-initialization=true")
	class Normal{
		@Autowired
		public AopMainServiceImpl main;
		@Autowired
		public AopSubServiceImpl sub;

		@Test
		public void normal() {
			StringJoiner param = new StringJoiner("");
			param.add("normal");
			StringJoiner rs = main.exec(param);

			sout(rs);

			String expected = "normal"
					+ "-(aop-proceed-before)"
					+ "-(main-before-sub)"
					+ "-(aop-proceed-before)"
					+ "-(sub-exec)"
					+ "-(aop-proceed-after)"
					+ "-(main-after-sub)"
					+ "-(aop-proceed-after)";

			assertThat(expected).isEqualTo(rs.toString());
			assertThat(_AOP_INDEX.get()).isEqualTo(7);
		}
	}

	@Nested
	@SpringBootTest(classes = MockitoApplication.class,
			properties = "spring.main.lazy-initialization=true")
	class MockBeanNotActiveAop{
		@SpyBean
		private AopMainServiceImpl spyMain;
		@MockBean
		private AopSubServiceImpl mockSub;

		@BeforeEach
		public void beforeEach(){
			MockitoAnnotations.openMocks(this);
		}

		/**
		 * mock-bean 无法触发 AOP
		 */
		@Test
		public void mock(){
			StringJoiner param = new StringJoiner("");
			param.add("spy");
			StringJoiner rs = spyMain.exec(param);

			String expected = "spy"
					+ "-(aop-proceed-before)"
					+ "-(main-before-sub)"
					+ "-(main-after-sub)"
					+ "-(aop-proceed-after)";

			sout(rs);

			assertThat(expected).isEqualTo(rs.toString());
			assertThat(_AOP_INDEX.get()).isEqualTo(4);
		}
	}

	@Nested
	@SpringBootTest(classes = MockitoApplication.class,
			properties = "spring.main.lazy-initialization=true")
	class SpyBeanCauseAopInvokeTwice {
		@SpyBean
		private AopMainServiceImpl spyMain;
		@SpyBean
		private AopSubServiceImpl spySub;

		@BeforeEach
		public void beforeEach(){
			MockitoAnnotations.openMocks(this);
		}
		/**
		 * 因为 mock 无法触发 AOP，所以尝试用 spy，
		 * 当同时需要`when(spy.method())`时，会触发2次 AOP！
		 */
		@Test
		public void invokeTwice(){
			StringJoiner param = new StringJoiner("");
			param.add("spy");

			// 这里会真实调用1次，同时触发 aop
			when(spySub.exec(param)).thenReturn(param.add("<(spy-then-return)"));

			// main 再次执行 `spySub.exec(param)` 不会真实调用，且使用上述 thenReturn。但是会触发AOP！
			StringJoiner rs = spyMain.exec(param);

			sout(rs);

			/*
			 * 1. when(spySub.exec(param)): 真实调用，且触发aop，此时 aop_index = 3；
			 *
			 * 2. spyMain.exec(param): 触发aop 到`sub.exec(...)` 此时 aop_index = 5；
			 *   因为`sub`被 when.thenReturn，所以不执行真实方法，但是触发aop，此时 aop_index = 7；
			 *
			 * 3. main 中调用 sub完成，且 main 执行aop完成，此时 aop_index = 9
			 */
			String expected = "spy"
					+ "-(aop-proceed-before)"
					+ "-(sub-exec)"
					+ "-(aop-proceed-after)<(spy-then-return)"
					+ "-(aop-proceed-before)"
					+ "-(main-before-sub)"
					+ "-(aop-proceed-before)"
					+ "-(aop-proceed-after)"
					+ "-(main-after-sub)"
					+ "-(aop-proceed-after)";

			assertThat(_AOP_INDEX.get()).isEqualTo(9);
		}
	}

	@Nested
	@SpringBootTest(classes = MockitoApplication.class,
			properties = "spring.main.lazy-initialization=true")
	class SpyAopCorrectTests {
		@SpyBean
		private AopMainServiceImpl spyMain;
		@SpyBean
		private AopSubServiceImpl spySub;

		@BeforeEach
		public void beforeEach(){
			MockitoAnnotations.openMocks(this);
		}

		/**
		 * <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications-mocking-beans">
		 *     Mocking and Spying Beans</a>
		 *
		 * <pre>
		 *   When you are using `@SpyBean` to spy on a bean that is proxied by Spring,
		 *   you may need to remove Spring’s proxy in some situations,
		 *   for example when setting expectations using given or when.
		 *   Use `AopTestUtils.getTargetObject(yourProxiedSpy)` to do so.
		 * </pre>
		 *
		 * <p>
		 * 备注：会因为 `when(...).thenReturn(...)`真实调用1次方法
		 *
		 * @see MockAopTests#exec()
		 */
		@Test
		public void exec(){
			StringJoiner param = new StringJoiner("");
			param.add(this.getClass().getSimpleName());

			// 可以理解为 获得原始的 `sub`对象
			AopSubServiceImpl targetObject = AopTestUtils.getTargetObject(spySub);

			// 因为是 真是对象，所以会 真实调用1次，但不会触发AOP！
			when(targetObject.exec(param)).thenReturn(param.add("<(spy-then-return)"));

			StringJoiner rs = spyMain.exec(param);

			sout(rs);

			String expected =
					  "SpyAopCorrectTests"
				    + "-(sub-exec)<(spy-then-return)"
				    + "-(aop-proceed-before)"
				    + "-(main-before-sub)"
				    + "-(aop-proceed-before)"
				    + "-(aop-proceed-after)"
				    + "-(main-after-sub)"
					+ "-(aop-proceed-after)";

			assertThat(rs.toString()).isEqualTo(expected);
		}
	}

	@Nested
	@SpringBootTest(classes = MockitoApplication.class,
			properties = "spring.main.lazy-initialization=true")
	class MockAopTests {
		@SpyBean
		private AopMainServiceImpl spyMain;
		@SpyBean
		private AopSubServiceImpl spySub;

		@BeforeEach
		public void beforeEach(){
			MockitoAnnotations.openMocks(this);
		}

		/**
		 * {@linkplain SpyAopCorrectTests#exec()} 一定会执行method，
		 * 但如果期望：一定不执行method，但需要触发aop。
		 *
		 * <p>思路：还是 spy-bean 保证被aop代理，但是将起真实的底层对象改成 mock-bean。
		 */
		@Test
		public void exec(){
			StringJoiner param = new StringJoiner("");
			param.add(this.getClass().getSimpleName());

			AopSubServiceImpl mockSub = mock(AopSubServiceImpl.class);
			/**
			 * {@link AopTestUtils#getTargetObject(Object)}
			 * {@link AopTestUtils#getUltimateTargetObject(Object)}
			 */
			ReflectionTestUtils.setField(((Advised) spySub).getTargetSource(), "target", mockSub);
			when(mockSub.exec(param)).thenReturn(param.add("<(spy-then-return)"));

			StringJoiner rs = spyMain.exec(param);

			sout(rs);

			String expected = "MockAopTests<(spy-then-return)"
							+ "-(aop-proceed-before)"
							+ "-(main-before-sub)"
							+ "-(aop-proceed-before)"
							+ "-(aop-proceed-after)"
							+ "-(main-after-sub)"
							+ "-(aop-proceed-after)";

			assertThat(rs.toString()).isEqualTo(expected);
		}
	}

	protected static void sout(StringJoiner joiner){
		System.out.println(joiner.toString().replaceAll("-\\(", "\n-("));
	}
}
