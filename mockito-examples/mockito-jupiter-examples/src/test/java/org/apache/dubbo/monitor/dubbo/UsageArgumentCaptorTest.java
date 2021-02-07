package org.apache.dubbo.monitor.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.monitor.Monitor;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.apache.dubbo.common.constants.CommonConstants.REFERENCE_FILTER_KEY;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * mockito 不支持mock `protected/final/static`方法，powermock现在(2021-02-04)还不支持 junit5和mockito3。
 * 所以，放到同一个package路径下。
 *
 * @author vergilyn
 * @since 2021-02-04
 *
 * @see <a href="https://github.com/apache/dubbo/blob/dubbo-2.7.8/dubbo-monitor/dubbo-monitor-default/src/test/java/org/apache/dubbo/monitor/dubbo/DubboMonitorFactoryTest.java">DubboMonitorFactoryTest</a>
 */
@SuppressWarnings({ "ResultOfMethodCallIgnored", "unchecked" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsageArgumentCaptorTest {
	private DubboMonitorFactory dubboMonitorFactory;

	@Mock
	private ProxyFactory proxyFactory;

	@BeforeEach
	public void setUp() throws Exception {
		openMocks(this);
		this.dubboMonitorFactory = new DubboMonitorFactory();
		this.dubboMonitorFactory.setProtocol(new DubboProtocol());
		this.dubboMonitorFactory.setProxyFactory(proxyFactory);
	}

	@Test
	public void testCreateMonitor() {
		URL urlWithoutPath = URL.valueOf("http://10.10.10.11");
		Monitor monitor = dubboMonitorFactory.createMonitor(urlWithoutPath);
		assertThat(monitor, not(nullValue()));

		URL urlWithFilterKey = URL.valueOf("http://10.10.10.11/").addParameter(REFERENCE_FILTER_KEY, "testFilter");
		monitor = dubboMonitorFactory.createMonitor(urlWithFilterKey);

		assertThat(monitor, not(nullValue()));
		ArgumentCaptor<Invoker> invokerArgumentCaptor = ArgumentCaptor.forClass(Invoker.class);
		verify(proxyFactory, atLeastOnce()).getProxy(invokerArgumentCaptor.capture());

		Invoker invoker = invokerArgumentCaptor.getValue();
		assertThat(invoker.getUrl().getParameter(REFERENCE_FILTER_KEY), containsString("testFilter"));
	}
}
