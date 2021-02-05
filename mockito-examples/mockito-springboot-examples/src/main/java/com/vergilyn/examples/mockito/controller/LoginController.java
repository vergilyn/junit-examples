package com.vergilyn.examples.mockito.controller;

import javax.servlet.http.HttpServletRequest;

import com.vergilyn.examples.mockito.dto.LoginDto;
import com.vergilyn.examples.mockito.service.LoginService;
import com.vergilyn.examples.mockito.simulate.SlowService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vergilyn
 * @since 2021-02-04
 */
@RestController
@RequestMapping
@Slf4j
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private SlowService slowService;

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String login(HttpServletRequest request, @RequestBody LoginDto loginDto){
		String username = loginDto.getUsername();
		String password = loginDto.getPassword();

		String rs = loginService.login(username, password);

		String vheader = request.getHeader("v-header");
		log.info("[vergilyn] >>>> request.getHeader('v-header') = {}", vheader);

		if (vheader != null){
			rs += "," + vheader;
		}

		return rs;
	}
}
