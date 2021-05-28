package com.vergilyn.examples.testng.springboot;

import java.util.List;

import com.vergilyn.examples.common.entity.UserEntity;
import com.vergilyn.examples.common.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * testng 需要继承 {@linkplain AbstractTestNGSpringContextTests}，之后就可以结合 {@linkplain SpringBootTest}
 * @author vergilyn
 * @date 2020-05-07
 * @see <a href="https://docs.spring.io/spring-boot/docs/2.2.11.RELEASE/reference/htmlsingle/#boot-features-testing">boot-features-testing</a>
 */
@SpringBootTest(classes = TestngSpringbootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TestngSpringbootTestng extends AbstractTestNGSpringContextTests {

	// FIXME 2020-11-25
	@Autowired
	private UserService testService;

	@org.testng.annotations.Test
	public void testService() {
		String username = "vergilyn";
		testService.save(username);

		List<UserEntity> users = testService.get(username);
		log.info("users: {}", users);
	}
}
