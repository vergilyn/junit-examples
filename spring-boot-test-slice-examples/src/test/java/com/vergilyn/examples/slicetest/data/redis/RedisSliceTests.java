package com.vergilyn.examples.slicetest.data.redis;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 *
 * {@linkplain DataRedisTest}:
 * <ul>
 *     <li>{@linkplain CacheAutoConfiguration}</li>
 *     <li>{@linkplain RedisAutoConfiguration}</li>
 *     <li>{@linkplain RedisRepositoriesAutoConfiguration}</li>
 * </ul>
 *
 *
 * @author vergilyn
 * @since 2021-05-27
 *
 * @see <a href="https://docs.spring.io/spring-boot/docs/2.2.11.RELEASE/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-redis-test">
 *          Auto-configured Data Redis Tests</a>
 * @see <a href="https://github.com/spring-projects/spring-boot/tree/v2.2.11.RELEASE/spring-boot-project/spring-boot-test-autoconfigure/src/test/java/org/springframework/boot/test/autoconfigure/data/redis">
 *          github, `@DataRedisTest` examples</a>
 */
@DataRedisTest
// 还是需要“RedisSliceTestApplication.class”
//@ContextConfiguration(classes = RedisSliceTestApplication.class)
public class RedisSliceTests {

	@Resource
	ApplicationContext applicationContext;

	// 需要依赖`spring-data-redis.jar`
	@Resource
	StringRedisTemplate stringRedisTemplate;

	@Test
	public void test(){
		System.out.println("applicationContext >>>> " + applicationContext);
		System.out.println("stringRedisTemplate >>>> " + stringRedisTemplate);
	}
}
