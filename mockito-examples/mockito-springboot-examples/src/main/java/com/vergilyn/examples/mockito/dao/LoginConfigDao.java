package com.vergilyn.examples.mockito.dao;

import org.springframework.stereotype.Repository;

@Repository
public class LoginConfigDao {
	private int maxErrorTimes = 5;

	public int getMaxErrorTimes() {
		return maxErrorTimes;
	}
}
