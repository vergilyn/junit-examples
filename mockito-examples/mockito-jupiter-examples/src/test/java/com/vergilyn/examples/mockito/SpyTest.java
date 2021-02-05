package com.vergilyn.examples.mockito;

import java.util.List;

import com.google.common.collect.Lists;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.atLeastOnce;

/**
 * @author vergilyn
 * @since 2021-02-04
 */
@SuppressWarnings({ "ResultOfMethodCallIgnored", "unchecked" })
public class SpyTest {
	private static final String _name = "vergilyn";

	/**
	 * Mockito 提供的 spy 方法可以包装一个 real-java-object，并返回一个包装后的新对象。
	 * 若<b>没有特别配置</b>的话，对这个新对象的所有方法调用，都会委派给实际的 Java 对象.
	 */
	@Test
	public void basic() {
		List<String> spy = Mockito.spy(Lists.newLinkedList());

		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {

			Mockito.when(spy.get(0)).thenReturn("none");

		}).printStackTrace();

		// optionally, you can stub out some methods:
		Mockito.when(spy.size()).thenReturn(100);

		// using the spy calls real methods
		spy.add("one");
		spy.add("two");

		// invoke real-methods, because this-method is not an option stub-method.
		Assertions.assertEquals("one", spy.get(0));
		Assertions.assertEquals("two", spy.get(1));

		// size() method was stubbed
		Assertions.assertEquals(100, spy.size());

		// optionally, you can verify
		Mockito.verify(spy, atLeastOnce()).add("one");
		Mockito.verify(spy, atLeastOnce()).add("two");
	}
}
