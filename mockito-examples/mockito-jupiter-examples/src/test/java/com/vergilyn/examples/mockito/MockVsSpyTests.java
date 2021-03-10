package com.vergilyn.examples.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 *
 * @author vergilyn
 * @since 2021-03-10
 */
public class MockVsSpyTests {

	/**
	 * mock: 不会调用任何真实的方法。<br/>
	 * spy: 若<b>没有特别配置</b>的话，对这个spy对象的所有方法调用，都会委派给实际的 Java 对象。
	 */
	@Test
	public void test(){
		TestObject mock = Mockito.mock(TestObject.class);
		when(mock.getDesc()).thenReturn("mock");
		mock.print();  // 不会打印

		// <=> Mockito.mock(TestObject.class, Answers.CALLS_REAL_METHODS)
		TestObject spy = Mockito.spy(TestObject.class);
		when(spy.getDesc()).thenReturn("spy");
		spy.print();  // method print >>>> desc: spy

	}

	private static class TestObject {

		public String getDesc() {
			return "";
		}

		public void print(){
			System.out.println("method print >>>> desc: " + getDesc());
		}
	}


}
