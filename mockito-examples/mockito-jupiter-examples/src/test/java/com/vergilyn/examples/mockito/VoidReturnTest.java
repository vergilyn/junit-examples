package com.vergilyn.examples.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

/**
 *
 * core: <br/>
 *   - {@linkplain Mockito#doNothing()} <br/>
 *   - {@linkplain Mockito#doCallRealMethod()} <br/>
 *   - {@linkplain Mockito#doAnswer(Answer)} <br/>
 *
 * @author vergilyn
 * @since 2021-06-17
 *
 * @see <a href="https://yanbin.blog/mockito-how-to-mock-void-method/#more-7748">Mockito如何mock返回值为`void`的方法</a>
 */
public class VoidReturnTest {

	@Test
	void syntax(){
		VoidReturnService spy = Mockito.spy(VoidReturnService.class);

		// 避免测试阶段的`check()`
		Mockito.doNothing().when(spy).check(ArgumentMatchers.any());

		Integer main = spy.incr(1);

		Assertions.assertEquals(main, 2);
	}


	private static class VoidReturnService {

		public Integer incr(Integer param){
			// e.g. 检查入参
			check(param);

			return param + 1;
		}

		public void check(Integer param) throws RuntimeException{
			 throw new UnsupportedOperationException();
		}
	}
}
