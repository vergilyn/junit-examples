# TestContainers

+ [testcontainers.org](https://www.testcontainers.org/)
+ [testcontainers](https://github.com/testcontainers)
+ [testcontainers-java](https://github.com/testcontainers/testcontainers-java)

使用案例参考，spring-boot-test中对`spring-data-redis @RedisHash`的测试：
[DataRedisTestIntegrationTests.java](https://github.com/spring-projects/spring-boot/blob/v2.3.6.RELEASE/spring-boot-project/spring-boot-test-autoconfigure/src/test/java/org/springframework/boot/test/autoconfigure/data/redis/DataRedisTestIntegrationTests.java)

- [用Testcontainers实现SpringBoot+Docker集成测试](https://www.jdon.com/51327)
- [基于testcontainers的现代化集成测试](https://zhuanlan.zhihu.com/p/62933843)
- [testcontainer-基本入门](https://blog.csdn.net/mail_liuxing/article/details/99075606)

- <https://docs.qq.com/doc/DUkRQTVZxU2pmQWxP>

## TestContainers是什么？
在我们开发的过程中，经常会用到一些第三方的产品或者中间件，如：redis，mq，db等等，
然而，因为这些产品的引入，测试过程变得复杂起来，
因为中间件的缺失（可能本地有，但是换了环境就没有了），导致测试用例编写很麻烦。

**TestContainer可以通过和docker结合**，让我们在编写测试用例的时候，
很方便的启动docker容器（容器内有我们需要的第三方产品），这样，我们就可以更好的完善测试用例。

## TestContainers 支持的测试框架？
原本只支持 junit4，但后续扩展支持 Jupiter/JUnit5。  
默认 `org.testcontainers:testcontainers-1.15.0.jar`中已经依赖了`junit:junit-4.12.jar`。

testcontainers集成junit5时需要依赖junit4，所以[/jupiter-testcontainers-examples/pom.xml](../jupiter-testcontainers-examples/pom.xml)
中需要依赖（否则编译错误 `找不到org.junit.rules.TestRule的类文件`）：
```
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
</dependency>
<!-- testcontainers 集成junit5时需要依赖junit4-->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
</dependency>
```


## FAQ
```
GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:5.0.10"))
      // Set the ports that this container listens on (不是指定 redis-server 启动使用的端口)
      .withExposedPorts(6379)
      .withCreateContainerCmdModifier(cmd -> {
         // 对主机端口和docker中的端口进行绑定，前面的是主机端口，后面的是docker中的端口
         cmd.getHostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(6380), new ExposedPort(6379)));
      })
      .waitingFor(Wait.forListeningPort());

redisContainer.start();
```

#### 1. `withExposedPorts(6379)`
指的是 docker创建的container对外暴露的port，不是指"指定redis-server的启动端口"。

#### 2. test-containers默认是随机端口
首先 container-redis-server.port = 6379，然后localhost会通过 随机端口(eg. 32001) 连接到 container-6379。
通过 create-container-cmd 可以修改绑定关系。

#### 3. test-contaner 如何连接已有的container？
如上demo，每次都会create临时的container，完成后DELETE。
（docker-desktop 中表现为 会 create-container，然后代码运行完，该container小时）

如果通过 docker-desktop 启动一个 redis:5.0.10，redis-server.port=6379，exposed.port=6380。
如果保持running，那么可以通过 `cmd> redis-cli -p 6380`连接（test-container create并start时，也可以这样连接），那么等价于local-redis。
（所以，test-container 的create-container应该表现为每次create-delete）
