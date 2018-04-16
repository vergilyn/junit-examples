package com.vergilyn.examples.junit.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/16
 */
@ActiveProfiles("development")  // 指定加载的profile
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations={"classpath*:/applicationContext-test-main.xml"}) // 加载主要的xml
// @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
public abstract class JUnit4BaseTest {
}
