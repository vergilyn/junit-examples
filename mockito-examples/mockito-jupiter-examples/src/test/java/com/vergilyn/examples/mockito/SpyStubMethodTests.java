package com.vergilyn.examples.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 *
 * @author vergilyn
 * @since 2021-03-11
 */
public class SpyStubMethodTests {

	/**
	 * 导致的结果：
	 * 如果`print()`被spring-aop拦截，那么其实会执行 2次 AOP。
	 */
	@Test
	public void valid(){
		TestObject spy = Mockito.spy(TestObject.class);

		// 1. 观察conlose 是否会打印1次，【会】打印“TestObject print >>>> 1”
		when(spy.print(1)).thenReturn(2);

		// 虽然`when(spy.print(1))`真实的执行了1次，但verify不这么认为
		Mockito.verify(spy, times(0)).print(1);

		// 2. 不会再次执行 `print(...)`（即未打印log），返回的结果 `2`
		System.out.println("expected: 2, actual: " + spy.print(1));
		// mockito 认为上一行中才是 invocations，所以总共 invocations `spy.print(1)` 1次（不是2次）
		Mockito.verify(spy, times(1)).print(1);

	}

	private static class TestObject {

		public Integer print(Integer i){
			System.out.println("TestObject print >>>> " + i);
			return i;
		}
	}
}
