package com.vergilyn.examples;

import java.util.List;

import com.vergilyn.examples.common.JavaTestApplication;
import com.vergilyn.examples.common.entity.UserEntity;
import com.vergilyn.examples.common.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
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
 *
 * <p> 2020-04-27
 *   springboot-test虽然可以指定启动类{@linkplain JavaTestApplication}，但是并不会执行{@linkplain JavaTestApplication#main(String[])}。
 *   所以可能导致导致`application-{profile}`未正常加载
 *   （当然可以不用{@linkplain SpringApplication#setAdditionalProfiles(String...)}，而在application.properties中配置）
 *
 *   另外一种方式是：{@linkplain TestPropertySource}记载配置文件，但是貌似不支持`.yml`形式。
 * </p>
 * @author vergilyn
 * @date 2020-04-27
 */
@SpringBootTest(classes= JavaTestApplication.class)
// @TestPropertySource(locations = {"/application-{profile}.properties"})
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
