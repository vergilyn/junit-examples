package com.vergilyn.examples.testng.springboot;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * <ul>
 *     <li>{@link AbstractTransactionalTestNGSpringContextTests}: 自动回滚事务。多数据源处理参考{@link AbstractRollbackTestng}</li>
 *     <li>{@link AbstractTestNGSpringContextTests}: 自动提交事务。暂时不知道怎么回滚事务，{@link Transactional}、{@link Rollback}并不能回滚事务</li>
 * </ul>
 *
 * TestNG默认提交事务; 暂时没研究出怎么回滚事务, 配置的<code>org.springframework.test.context.transaction.TransactionConfiguration</code>、{@link Rollback}无效
 */
@ActiveProfiles("development")
@ContextConfiguration(locations = { "classpath:applicationContext-test-main.xml" })
// @TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public abstract class AbstractSpringTestng extends AbstractTestNGSpringContextTests {

}
