package io.grpc.internal;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.google.common.util.concurrent.MoreExecutors;

import io.grpc.ClientCall;
import io.grpc.ClientCall.Listener;
import io.grpc.Deadline;
import io.grpc.Metadata;
import io.grpc.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 *
 * @author vergilyn
 * @since 2021-02-04
 *
 * @see <a href="https://github.com/grpc/grpc-java/blob/v1.35.0/core/src/test/java/io/grpc/internal/DelayedClientCallTest.java">DelayedClientCallTest</a>
 */
public class UsageArgumentCaptorTest {

	// 规则 MockitoRule 会自动帮我们调用 MockitoAnnotations.initMocks(this) 去实例化出 注解 的成员变量，我们就无需手动进行初始化了。
	// 所以等价于 `before()`
	// @org.junit.Rule  // junit4写法
	// public final MockitoRule mockitoRule = MockitoJUnit.rule();

	@Mock
	private ClientCall<String, Integer> mockRealCall;
	@Mock
	private ClientCall.Listener<Integer> listener;
	@Captor
	ArgumentCaptor<Status> statusCaptor;

	private final Executor callExecutor = MoreExecutors.directExecutor();
	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(8);

	@BeforeEach
	public void before(){
		openMocks(this);
	}

	@Test
	public void listenerEventsPropagated() {
		DelayedClientCall<String, Integer> delayedClientCall = new DelayedClientCall<>(
				callExecutor, scheduledExecutorService, Deadline.after(10, SECONDS));

		delayedClientCall.start(listener, new Metadata());
		delayedClientCall.setCall(mockRealCall);

		ArgumentCaptor<Listener<Integer>> listenerCaptor = ArgumentCaptor.forClass(null);
		verify(mockRealCall).start(listenerCaptor.capture(), any(Metadata.class));

		Listener<Integer> realCallListener = listenerCaptor.getValue();
		Metadata metadata = new Metadata();
		metadata.put(Metadata.Key.of("key", Metadata.ASCII_STRING_MARSHALLER), "value");

		realCallListener.onHeaders(metadata);
		verify(listener).onHeaders(metadata);

		realCallListener.onMessage(3);
		verify(listener).onMessage(3);

		realCallListener.onReady();
		verify(listener).onReady();

		Metadata trailer = new Metadata();
		trailer.put(Metadata.Key.of("key2", Metadata.ASCII_STRING_MARSHALLER), "value2");

		realCallListener.onClose(Status.DATA_LOSS, trailer);
		verify(listener).onClose(statusCaptor.capture(), eq(trailer));

		assertEquals(Status.Code.DATA_LOSS, statusCaptor.getValue().getCode());
	}
}
