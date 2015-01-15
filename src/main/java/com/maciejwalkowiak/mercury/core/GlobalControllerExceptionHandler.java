package com.maciejwalkowiak.mercury.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

/**
 * Converts exceptions to user friendly error messages.
 *
 * Inspired by http://www.jayway.com/2012/09/16/improve-your-spring-rest-api-part-i/
 *
 * @author Maciej Walkowiak
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorMessage handleException(MethodArgumentNotValidException ex) {
		Stream<String> fieldErrorsStream = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ", " + error.getDefaultMessage());

		Stream<String> globalErrorsStream = ex.getBindingResult().getGlobalErrors().stream()
				.map(error -> error.getObjectName() + ", " + error.getDefaultMessage());

		List<String> errors = concat(fieldErrorsStream, globalErrorsStream).collect(Collectors.toList());

		return new ErrorMessage(errors);
	}

	private static class ErrorMessage {
		private final List<String> errors;

		public ErrorMessage(List<String> errors) {
			this.errors = errors;
		}

		public List<String> getErrors() {
			return errors;
		}
	}
}
