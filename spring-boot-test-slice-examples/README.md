# spring-boot-test-slice-examples

+ [Auto-configured Tests](https://docs.spring.io/spring-boot/docs/2.2.11.RELEASE/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-tests)

> Spring Boot’s auto-configuration system works well for applications but can sometimes be a little too much for tests.
> It often helps to load only the parts of the configuration that are required to test a **“slice”** of your application.  
> For example, you might want to test that Spring MVC controllers are mapping URLs correctly,
> and you do not want to involve database calls in those tests, or you might want to test JPA entities,  
> and you are not interested in the web layer when those tests run.
>
> The `spring-boot-test-autoconfigure` module includes a number of annotations that can be used to automatically configure such “slices”.  
> Each of them works in a similar way, providing a `@…Test`(eg. `@DataRedisTest` or `@JsonTest`) annotation
> that loads the ApplicationContext and one or more `@AutoConfigure…` annotations that can be used to customize auto-configuration settings.


## Q&A
### 例如`@DataRedisTest` 和 `SpringBootApplication`
如果只使用`@DataRedisTest`，且可扫描package下**不包含**`@SpringBootApplication`时：
```TEXT

java.lang.IllegalStateException: Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes=...) with your test

	at org.springframework.util.Assert.state(Assert.java:76)
	at org.springframework.boot.test.context.SpringBootTestContextBootstrapper.getOrFindConfigurationClasses(SpringBootTestContextBootstrapper.java:235)
	at org.springframework.boot.test.context.SpringBootTestContextBootstrapper.processMergedContextConfiguration(SpringBootTestContextBootstrapper.java:151)
	at org.springframework.test.context.support.AbstractTestContextBootstrapper.buildMergedContextConfiguration(AbstractTestContextBootstrapper.java:395)
	at org.springframework.test.context.support.AbstractTestContextBootstrapper.buildDefaultMergedContextConfiguration(AbstractTestContextBootstrapper.java:312)
	at org.springframework.test.context.support.AbstractTestContextBootstrapper.buildMergedContextConfiguration(AbstractTestContextBootstrapper.java:265)
	at org.springframework.test.context.support.AbstractTestContextBootstrapper.buildTestContext(AbstractTestContextBootstrapper.java:108)
	at org.springframework.boot.test.context.SpringBootTestContextBootstrapper.buildTestContext(SpringBootTestContextBootstrapper.java:101)
	at org.springframework.test.context.TestContextManager.<init>(TestContextManager.java:137)
	at org.springframework.test.context.TestContextManager.<init>(TestContextManager.java:122)
	at org.junit.jupiter.engine.execution.ExtensionValuesStore.lambda$getOrComputeIfAbsent$0(ExtensionValuesStore.java:81)
	at org.junit.jupiter.engine.execution.ExtensionValuesStore$MemoizingSupplier.get(ExtensionValuesStore.java:182)
	at org.junit.jupiter.engine.execution.ExtensionValuesStore.getOrComputeIfAbsent(ExtensionValuesStore.java:84)
	at org.junit.jupiter.engine.execution.ExtensionValuesStore.getOrComputeIfAbsent(ExtensionValuesStore.java:88)
	at org.junit.jupiter.engine.execution.NamespaceAwareStore.getOrComputeIfAbsent(NamespaceAwareStore.java:61)
	at org.springframework.test.context.junit.jupiter.SpringExtension.getTestContextManager(SpringExtension.java:213)
	at org.springframework.test.context.junit.jupiter.SpringExtension.beforeAll(SpringExtension.java:77)
	...
	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:54)
	Suppressed: java.lang.IllegalStateException: Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes=...) with your test
		at org.springframework.util.Assert.state(Assert.java:76)
		at org.springframework.boot.test.context.SpringBootTestContextBootstrapper.getOrFindConfigurationClasses(SpringBootTestContextBootstrapper.java:235)
		at org.springframework.boot.test.context.SpringBootTestContextBootstrapper.processMergedContextConfiguration(SpringBootTestContextBootstrapper.java:151)
		at org.springframework.test.context.support.AbstractTestContextBootstrapper.buildMergedContextConfiguration(AbstractTestContextBootstrapper.java:395)
		at org.springframework.test.context.support.AbstractTestContextBootstrapper.buildDefaultMergedContextConfiguration(AbstractTestContextBootstrapper.java:312)
		at org.springframework.test.context.support.AbstractTestContextBootstrapper.buildMergedContextConfiguration(AbstractTestContextBootstrapper.java:265)
		at org.springframework.test.context.support.AbstractTestContextBootstrapper.buildTestContext(AbstractTestContextBootstrapper.java:108)
		at org.springframework.boot.test.context.SpringBootTestContextBootstrapper.buildTestContext(SpringBootTestContextBootstrapper.java:101)
		at org.springframework.test.context.TestContextManager.<init>(TestContextManager.java:137)
		at org.springframework.test.context.TestContextManager.<init>(TestContextManager.java:122)
		at org.junit.jupiter.engine.execution.ExtensionValuesStore.lambda$getOrComputeIfAbsent$0(ExtensionValuesStore.java:81)
		at org.junit.jupiter.engine.execution.ExtensionValuesStore$MemoizingSupplier.get(ExtensionValuesStore.java:182)
		at org.junit.jupiter.engine.execution.ExtensionValuesStore.remove(ExtensionValuesStore.java:98)
		at org.junit.jupiter.engine.execution.NamespaceAwareStore.remove(NamespaceAwareStore.java:73)
		at org.springframework.test.context.junit.jupiter.SpringExtension.afterAll(SpringExtension.java:89)
		at org.junit.jupiter.engine.descriptor.ClassBasedTestDescriptor.lambda$invokeAfterAllCallbacks$13(ClassBasedTestDescriptor.java:421)
		at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
		at org.junit.jupiter.engine.descriptor.ClassBasedTestDescriptor.lambda$invokeAfterAllCallbacks$14(ClassBasedTestDescriptor.java:421)
		at java.util.ArrayList.forEach(ArrayList.java:1259)
		at org.junit.jupiter.engine.descriptor.ClassBasedTestDescriptor.invokeAfterAllCallbacks(ClassBasedTestDescriptor.java:421)
		at org.junit.jupiter.engine.descriptor.ClassBasedTestDescriptor.after(ClassBasedTestDescriptor.java:213)
		at org.junit.jupiter.engine.descriptor.ClassBasedTestDescriptor.after(ClassBasedTestDescriptor.java:77)
		at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:145)
		at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
		at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:145)
		...

```