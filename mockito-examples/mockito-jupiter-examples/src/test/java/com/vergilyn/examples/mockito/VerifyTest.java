package com.vergilyn.examples.mockito;

import java.util.List;

import com.sun.org.glassfish.gmbal.Description;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

/**
 *
 * @author vergilyn
 * @since 2021-02-04
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "unchecked"})
public class VerifyTest {

	/**
	 * <ul>
	 *     <li>{@linkplain Mockito#times(int)}</li>
	 *     <li>{@linkplain Mockito#atLeastOnce()}</li>
	 *     <li>{@linkplain Mockito#atLeast(int)}</li>
	 *     <li>{@linkplain Mockito#atMostOnce()}</li>
	 *     <li>{@linkplain Mockito#atMost(int)}</li>
	 *     <li>{@linkplain Mockito#never()}</li>
	 *     <li>{@linkplain Mockito#inOrder(Object...)}</li>
	 *     <li>...</li>
	 * </ul>
	 *
	 * @see org.mockito.verification.VerificationMode
	 */
	@Test
	@Description("Verifies certain behavior happened at least once / exact number of times / never.")
	public void basic(){
		List<Integer> mockList = mock(List.class);

		// 第1次调用 get(0) 返回`1`，第1次调用 get(0) 返回`2`。依次类推
		Mockito.when(mockList.get(0)).thenReturn(1).thenReturn(2);
		assertEquals(1, mockList.get(0));
		assertEquals(2, mockList.get(0));

		Mockito.verify(mockList, times(2))
				.get(0);

		Mockito.verify(mockList, never())
				.get(1);
	}

	@Test
	@Description("Creates InOrder object that allows verifying mocks in order.")
	public void inOrder(){
		List<Integer> mockList = mock(List.class);

		// inOrder: mockList[1] > mockList[0]
		assertNull(mockList.get(1));
		assertNull(mockList.get(0));

		InOrder inOrder = Mockito.inOrder(mockList);

		inOrder.verify(mockList).get(1);
		inOrder.verify(mockList).get(0);

	}

	@Test
	public void inOrders() {
		List<Integer> firstMock = mock(List.class);
		List<Integer> secondMock = mock(List.class);

		// inOrders: first[1] > second[0] > second[1] > first[0]
		assertNull(firstMock.get(1));
		assertNull(secondMock.get(0));
		assertNull(secondMock.get(1));
		assertNull(firstMock.get(0));

		InOrder inOrder = Mockito.inOrder(firstMock, secondMock);

		inOrder.verify(firstMock).get(1);
		inOrder.verify(secondMock).get(0);

		inOrder.verify(secondMock).get(1);
		inOrder.verify(firstMock).get(0);
	}

	/**
	 * {@linkplain Mockito#verifyNoMoreInteractions}:
	 * Checks if any of given mocks has any unverified interaction(交互).
	 * You can use this method after you verified your mocks - to make sure that nothing else was invoked on your mocks
	 */
	@Test
	public void noMoreInteractions(){
		List<Integer> firstMock = mock(List.class);

		// interaction(交互)
		firstMock.get(0);
		firstMock.clear();

		// verification
		Mockito.verify(firstMock, atLeastOnce()).get(anyInt());

		// following will fail because 'clear()' is unexpected
		NoInteractionsWanted noInteractionsWanted = assertThrows(NoInteractionsWanted.class, () -> {
			Mockito.verifyNoMoreInteractions(firstMock);
		});

		noInteractionsWanted.printStackTrace();
	}

}
