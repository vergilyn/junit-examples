package com.vergilyn.examples.mockito.dao;

import java.util.Map;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

/**
 *
 * @author vergilyn
 * @since 2021-02-04
 */
@Repository
public class AccountDao {
	private static Map<String, String> _data;

	static {
		_data = Maps.newHashMap();
		_data.put("vergilyn", "409839163");
	}
	public String getPassword(String username){
		return _data.get(username);
	}
}
