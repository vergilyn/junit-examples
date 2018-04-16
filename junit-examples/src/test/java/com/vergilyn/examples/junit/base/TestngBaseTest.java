package com.vergilyn.examples.junit.base;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * TestNG默认提交事务; 暂时没研究出怎么回滚事务, 配置的{@link TransactionConfiguration}、{@link Rollback}无效
 */
@ActiveProfiles("development")
@ContextConfiguration(locations = { "classpath:applicationContext-test-main.xml" })
// @TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class TestngBaseTest extends AbstractTestNGSpringContextTests {
}
