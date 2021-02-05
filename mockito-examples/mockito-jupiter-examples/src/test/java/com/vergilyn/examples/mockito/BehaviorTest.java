package com.vergilyn.examples.mockito;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BehaviorTest {

	/**
	 * mcok 行为验证
	 */
	@Test
	public void behavior(){
		List<Integer> list = Mockito.mock(List.class);

		Mockito.when(list.get(0)).thenReturn(0);


	}
}
