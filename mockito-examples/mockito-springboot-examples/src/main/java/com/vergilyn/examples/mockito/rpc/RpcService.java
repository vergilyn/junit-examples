package com.vergilyn.examples.mockito.rpc;


/**
 * 依赖的外部服务/方法，例如 dubbo/feign、第三方API请求返回结果等。
 * @author vergilyn
 * @since 2021-02-04
 */
public interface RpcService {

	String doSomething(String username);
}
