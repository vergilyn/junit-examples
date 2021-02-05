package com.vergilyn.examples.mockito;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * 参数捕获，Mockito 允准我们捕获一个 Mock 对象的方法调用所传递的参数。
 *
 * @author vergilyn
 * @since 2021-02-04
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "unchecked"})
public class ArgumentCaptorTest {

	@Test
	public void basic(){
		List<Integer> mockList = mock(List.class);

		int arg = RandomUtils.nextInt();
		mockList.get(arg);

		ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);

		verify(mockList, atLeastOnce()).get(argumentCaptor.capture());

		// getValue() <=> this.capturingMatcher.getLastValue()
		System.out.printf("basic() >>>> arg: %d, captor.value: %d \n", arg, argumentCaptor.getValue());

		Assertions.assertEquals(arg, argumentCaptor.getValue());
		Assertions.assertEquals(0, argumentCaptor.capture());
	}

}
