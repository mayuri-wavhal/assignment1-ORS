package com.assignment1.retailstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomNotFoundException() {
	}

	public CustomNotFoundException(String message) {
		super(message);
	}

	public CustomNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
