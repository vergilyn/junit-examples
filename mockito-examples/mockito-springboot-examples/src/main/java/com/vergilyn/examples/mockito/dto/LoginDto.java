package com.vergilyn.examples.mockito.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author vergilyn
 * @since 2021-02-04
 */
@Data
@NoArgsConstructor
public class LoginDto implements Serializable {

	private String username;
	private String password;

	public LoginDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
