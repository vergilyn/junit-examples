package com.vergilyn.examples.mockito;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author vergilyn
 * @since 2021-02-07
 */
public class ArgumentMatcherTest {

	@Test
	public void argsMatcher(){
		Map<String, Integer> mockMap = mock(Map.class);

		Integer expected = 0;
		when(mockMap.get(anyString())).thenReturn(expected);

		Integer actual = mockMap.get("vergilyn");
		System.out.println(" >>>> " + actual);

		assertEquals(expected, actual);
	}


	@Test
	public void incorrect(){
		Map<String, String> mockMap = mock(Map.class);

		String expected = "mockito-argument";

		// When using matchers, all arguments have to be provided by matchers.
		Assertions.assertThrows(InvalidUseOfMatchersException.class, () -> {

			// incorrect:
			when(mockMap.put(anyString(), expected)).thenReturn(expected);

			// correct:
			// when(mockMap.put(anyString(), anyString())).thenReturn(expected);
			// when(mockMap.put(anyString(), eq(expected))).thenReturn(expected);

		}).printStackTrace();

	}

}
