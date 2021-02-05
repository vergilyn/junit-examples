package com.vergilyn.examples.mockito;

import java.util.List;

import com.sun.org.glassfish.gmbal.Description;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;

/**
 * {@linkplain Mockito#mock(Class)}} 操作的全是`仿造`的虚假对象，
 * 即使`list.add(0, "invalid")`后，`list.get(0)`还是<b>"vergilyn"</b>。
 *
 * <br/>
 * 与 {@code mock}相对应的是：{@linkplain Mockito#spy(Class)}，<b>提供真实的对象</b>。
 *
 * @author vergilyn
 * @since 2021-02-03
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "unchecked"})
public class MockTest {
	private static final String _name = "vergilyn";

	/**
	 * {@linkplain Mockito#mock(Class)} 是`仿造`的假对象，所以`list.size`返回的是 0。
	 */
	@Test
	public void mock() {
		// 创建 Mock 对象。
		List<String> list = Mockito.mock(List.class);
		
		// 设置预期，当调用 get(0) 方法时返回 "vergilyn"
		// 该方法就是 Stub，替换实际的操作 `java.util.List#get()`。
		Mockito.when(list.get(0)).thenReturn(_name);

		assertEquals(list.get(0), _name);

		assertNull(list.get(1));

		// size = 0!
		assertEquals(0, list.size());

		// 对 Mock 对象设置无效
		list.add(0, "invalid");
		assertEquals(list.get(0), _name);  // get(0) 还是"vergilyn"，而不是"invalid"

		System.out.println("get(0): " + list.get(0));
		System.out.println("get(1): " + list.get(1));
		System.out.println("get(2): " + list.get(2));
		System.out.println("size(): " + list.size());
	}

	@Test
	@Description("Sets a return value to be returned when the method is called.")
	public void thenReturn(){
		List<Integer> list = Mockito.mock(List.class);

		// 第1次调用 get(0) 返回`1`，第1次调用 get(0) 返回`2`。依次类推
		Mockito.when(list.get(0)).thenReturn(1).thenReturn(2);
		assertEquals(1, list.get(0));
		assertEquals(2, list.get(0));

		// 不同的写法
		Mockito.when(list.get(0)).thenReturn(3, 4, 5);
		assertEquals(3, list.get(0));
		assertEquals(4, list.get(0));
		assertEquals(5, list.get(0));

		// 不同的写法
		List<Integer> spy = Mockito.spy(List.class);
		Mockito.doReturn(6)
				.doReturn(7)
				.doReturn(null)
				.when(spy).get(anyInt());  // anyInt() 参数配置

		// 都不会 throw-exception
		assertEquals(6, spy.get(Integer.MIN_VALUE));
		assertEquals(7, spy.get(Integer.MIN_VALUE));

		assertNull(spy.get(RandomUtils.nextInt()));
	}

}
