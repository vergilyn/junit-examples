package com.vergilyn.examples.jupiter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 期望异常测试
 * @author vergilyn
 * @since 2021-01-27
 */
public class ExpectedExceptionJupiter {

	@Test
	public void failure(){
		// Expected java.lang.NullPointerException to be thrown, but nothing was thrown.
		assertThrows(NullPointerException.class, () -> method(false));
	}

	@Test
	public void success(){
		assertThrows(NullPointerException.class, () -> method(true));
	}

	private void method(boolean bool) throws NullPointerException{
		if (bool){
			throw new NullPointerException();
		}
	}
}
