package com.vergilyn.examples.mockito.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author vergilyn
 * @see org.springframework.web.bind.annotation.ControllerAdvice
 * @since 2021-02-05
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String error(Exception e) {
		e.printStackTrace();

		return "error message: " + e.getMessage();
	}

	@ExceptionHandler(NullPointerException.class)
	public String error(NullPointerException e) {
		e.printStackTrace();

		return "NullPointerException message: " + e.getMessage();
	}
}
