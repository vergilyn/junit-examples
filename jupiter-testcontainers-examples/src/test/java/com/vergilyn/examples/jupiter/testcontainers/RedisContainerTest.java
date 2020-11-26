package com.vergilyn.examples.jupiter.testcontainers;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;

/**
 * @see <a href="https://www.testcontainers.org/test_framework_integration/junit_5/">testcontainers integration junit5</a>
 * @see <a href="https://www.testcontainers.org/test_framework_integration/manual_lifecycle_control/#singleton-containers">singleton container pattern</a>
 * @see <a href="https://www.testcontainers.org/test_framework_integration/junit_5/#adding-testcontainers-junit-5-support-to-your-project-dependencies">Adding Testcontainers JUnit 5 support to your project dependencies</a>
 */
@Testcontainers(disabledWithoutDocker = true)
public class RedisContainerTest {

	/* To define a restarted container, define an instance field inside your test class
	 * and annotate it with the `@Container` annotation.
	 */
	@Container
	public GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:5.0.10"))
			// Set the ports that this container listens on (不是指定 redis-server 启动使用的端口)
			.withExposedPorts(6379)
			.withCreateContainerCmdModifier(cmd -> {
				// 对主机端口和docker中的端口进行绑定，前面的是主机端口，后面的是docker中的端口
				cmd.getHostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(16379), new ExposedPort(6379)));
			})
			.waitingFor(Wait.forListeningPort());

	private Jedis jedis;

	@BeforeEach
	public void setUp(){
		System.out.println("setUp() >>>> " + redisContainer.isRunning());

		String address = redisContainer.getHost();
		Integer port = redisContainer.getFirstMappedPort();
		jedis = new Jedis(address, port);
	}

	@Test
	public void test(){
		System.out.println("redis-container.host >>>> " + redisContainer.getHost());
		System.out.println("redis-container.first-mapped-port >>>> " + redisContainer.getFirstMappedPort());

		Client jedisClient = jedis.getClient();
		System.out.println("jedis.host >>>> " + jedisClient.getHost());
		System.out.println("jedis.port >>>> " + jedisClient.getPort());

		String key = "vergilyn:junit:incr";
		Long incr = jedis.incr(key);
		System.out.println("vergilyn:junit:incr >>>> " + incr);

		incr = jedis.incrBy(key, 10);
		System.out.println("vergilyn:junit:incr >>>> " + incr);
	}

}
