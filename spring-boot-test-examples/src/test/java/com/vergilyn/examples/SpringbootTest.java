package com.vergilyn.examples;

import java.util.List;

import com.vergilyn.examples.common.JavaTestApplication;
import com.vergilyn.examples.common.entity.UserEntity;
import com.vergilyn.examples.common.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 *
 * <p> 2020-04-27
 *   springboot-test虽然可以指定启动类{@linkplain JavaTestApplication}，但是并不会执行{@linkplain JavaTestApplication#main(String[])}。
 *   所以可能导致导致`application-{profile}`未正常加载
 *   （当然可以不用{@linkplain SpringApplication#setAdditionalProfiles(String...)}，而在application.properties中配置）
 * <p>
 *   另外一种方式是：{@linkplain TestPropertySource}记载配置文件，但是貌似不支持`.yml`形式。
 * </p>
 *
 * @author vergilyn
 * @date 2020-04-27
 * @see <a href="https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-testing">boot-features-testing</a>
 */
@SpringBootTest(classes = JavaTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// @TestPropertySource(locations = {"/application-{profile}.properties"})
@Slf4j
public class SpringbootTest {
    @Autowired
    private UserService testService;

    // spring boot 2.x 依赖 junit 5.x
    @org.junit.jupiter.api.Test
    public void testService() {
        String username = "vergilyn";
        testService.save(username);

        List<UserEntity> users = testService.get(username);
        log.info("users: {}", users);
    }

}
