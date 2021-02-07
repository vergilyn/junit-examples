package com.vergilyn.examples.mockito;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

/**
 * mockito-3.4+ support mock static-method(need `mockito-inline`).
 *
 * <p>
 * See examples in javadoc for {@link Mockito} class
 *
 * @author vergilyn
 * @since 2021-02-07
 */
public class StaticMethodMockTest {

	@Test
	public void mockStaticMethod() {
		Integer[] arrays = {1, 2};

		MockedStatic<StringUtils> mockStatic = Mockito.mockStatic(StringUtils.class);

		mockStatic.when(() -> {
			// 不能使用 org.mockito.ArgumentMatchers#...()
			StringUtils.join(eq(arrays), anyString());
		}).thenReturn("3");

		String expected = StringUtils.join(arrays, "anyString()");
		System.out.println(" >>>> " + expected);

		Assertions.assertEquals("3", expected);

		/*
		 * The returned object's `MockedStatic.close()` method must be called
		 * upon completing the test or the mock will remain active on the current thread.
		 */
		mockStatic.close();
	}

	/**
	 * {@linkplain Mockito Mockito-javadoc#39} Some methods <b>cannot</b> be mocked:
	 * <ul>
	 *     <li>Package-visible methods of java.*</li>
	 *     <li>native methods</li>
	 * </ul>
	 *
	 * @see <a href="https://github.com/mockito/mockito/issues/2011">Stackoverflow error when upgrading to v3.5.2</a>
	 */
	@Test
	@Disabled
	public void errorTest(){
		// The used MockMaker SubclassByteBuddyMockMaker does not support the creation of static mocks
		Assertions.assertThrows(MockitoException.class, () -> {

			Mockito.mockStatic(Math.class);

		}).printStackTrace();
	}

}
