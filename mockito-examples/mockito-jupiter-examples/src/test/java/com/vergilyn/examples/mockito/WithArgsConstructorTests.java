package com.vergilyn.examples.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author vergilyn
 * @since 2021-03-10
 */
public class WithArgsConstructorTests {

	@Test
	public void constructor(){
		// 因为mock都是"虚假"对象，所以没有任何影响（即使不提供 no-args constructor）
		TestObject mock = Mockito.mock(TestObject.class);
		System.out.println("mock.desc: " + mock.desc);

		// <=> Answers.CALLS_REAL_METHODS
		TestObject spy1 = Mockito.spy(new TestObject("test"));
		System.out.println("spy1.desc: " + spy1.desc);

		TestObject spy2 = Mockito.mock(TestObject.class,
				Mockito.withSettings()
						.useConstructor("with-settings")
						.defaultAnswer(Answers.CALLS_REAL_METHODS));
		System.out.println("spy2.desc: " + spy2.desc);

		// error
		assertThrows(MockitoException.class, () -> {
			TestObject spy3 = Mockito.spy(TestObject.class);
			System.out.println("spy3.desc: " + spy3.desc);
		}).printStackTrace();
	}

	private static class TestObject {
		private final String desc;

		public TestObject(String desc) {
			this.desc = desc;
		}
	}
}
