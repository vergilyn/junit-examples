package com.vergilyn.examples;

import javax.sql.DataSource;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

/**
 * TestNG默认回滚事务<br/>
 * {@link org.springframework.test.annotation.Rollback} 指定是否回滚事务。
 */
@ActiveProfiles("development")
@ContextConfiguration(locations = {"classpath:applicationContext-test-main.xml" })
public abstract class AbstractRollbackTestng extends AbstractTransactionalTestNGSpringContextTests {

    /* 多数据源需要指定回滚的数据源是哪个; */
    @Override
    public void setDataSource(/*@Qualifier("dataSource-name")*/ DataSource dataSource) {
        super.setDataSource(dataSource);
    }

}
