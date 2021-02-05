package com.vergilyn.examples.mockito.service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import com.google.common.collect.Maps;
import com.vergilyn.examples.mockito.AbstractSleepPrint;
import com.vergilyn.examples.mockito.dao.LoginConfigDao;
import com.vergilyn.examples.mockito.rpc.RpcService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vergilyn
 * @since 2021-02-04
 */
@Service
@Slf4j
public class LoginService extends AbstractSleepPrint {

	@Autowired
	private AccountService accountService;
	@Autowired
	private LoginConfigDao loginConfigDao;

	private RpcService rpcService;

	private Map<String, AtomicInteger> _limit = Maps.newHashMap();

	@PostConstruct
	public void init(){
		sleep(login_service);
	}

	public String login(String username, String password){
		log.info("[vergilyn] login() >>>> username: {}, password: {}", username, password);

		if (isLimit(username)){
			return Boolean.FALSE.toString();
		}

		boolean isMatch = accountService.isMatch(username, password);

		if (!isMatch){
			int errorTimes = incrErrorTimes(username);
			log.error("[vergilyn] >>>> [{}]: input password error-times {}.", username, errorTimes);
		}

		String rcpResult = rpcService.doSomething(username);
		log.info("[vergilyn] >>>> rpc-result: {}", rcpResult);

		return Boolean.valueOf(isMatch).toString();
	}

	public boolean isLimit(String username){
		int maxErrorTime = loginConfigDao.getMaxErrorTimes();
		log.info("[vergilyn] >>>> login config max-error-times: {}", maxErrorTime);

		int errorTimes = errorTimes(username);
		if (errorTimes > maxErrorTime){
			log.error("[vergilyn] >>>> [{}] input error-password times > {}, error-times: {}",
					username, maxErrorTime, errorTimes);
			return true;
		}

		return false;
	}

	// mockito not support private/protected/static/final methods.
	private int errorTimes(String username){
		return _limit.computeIfAbsent(username, s -> new AtomicInteger(0))
				.get();
	}

	public int incrErrorTimes(String username){
		log.error("[vergilyn] >>>> [{}] password input error.", username);

		return _limit.computeIfAbsent(username, s -> new AtomicInteger(0))
					.incrementAndGet();
	}
}
