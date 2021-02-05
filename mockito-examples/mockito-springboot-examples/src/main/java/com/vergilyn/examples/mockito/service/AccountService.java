package com.vergilyn.examples.mockito.service;

import com.vergilyn.examples.mockito.dao.AccountDao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vergilyn
 * @since 2021-02-04
 */
@Service
@Slf4j
public class AccountService {

	@Autowired
	private AccountDao accountDao;

	public boolean isMatch(String username, String password){
		log.info("[vergilyn] isMatch() >>>> username: {}, password: {}", username, password);

		return StringUtils.equals(password, accountDao.getPassword(username));
	}
}
