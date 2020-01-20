package com.vergilyn.examples;

import java.util.List;

import com.vergilyn.examples.common.JavaTestApplication;
import com.vergilyn.examples.common.entity.UserEntity;
import com.vergilyn.examples.common.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

/**
 * TestNG并发测试<br/>
 * <ul>
 *     <li>{@link AbstractTransactionalTestNGSpringContextTests}: 自动回滚事务。多数据源处理参考{@link AbstractRollbackTestng}</li>
 *     <li>{@link AbstractTestNGSpringContextTests}: 自动提交事务。暂时不知道怎么回滚事务，{@link Transactional}、{@link Rollback}并不能回滚事务</li>
 * </ul>
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/16
 */
@SpringBootTest(classes= JavaTestApplication.class)
@Slf4j
public class SpringbootTestng {
    @Autowired
    private UserService testService;

    @Test(threadPoolSize=2, invocationCount=5)
    public void testService(String username){
        testService.save(username);

        List<UserEntity> users = testService.get(username);
        log.info("users: {}", users);
    }

}
