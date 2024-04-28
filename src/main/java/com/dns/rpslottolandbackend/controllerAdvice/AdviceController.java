package com.dns.rpslottolandbackend.controllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import lombok.NoArgsConstructor;

@ControllerAdvice
public class AdviceController {
	
	@ResponseBody
	@ExceptionHandler(HandlerMethodValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorFormat badHeaders(HandlerMethodValidationException ex, HttpServletRequest request) {
		return toError(ex, request);
	}

	@ResponseBody
	@ExceptionHandler(MissingRequestHeaderException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorFormat badHeaders(MissingRequestHeaderException ex, HttpServletRequest request) {
		return toError(ex, request);
	}
	

	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorFormat badConstraint(ConstraintViolationException ex, HttpServletRequest request) {
		return toError(ex, request);
	
	}
	
	private ErrorFormat toError(ServletRequestBindingException ex, HttpServletRequest request) {
		ErrorFormat error = new ErrorFormat();
		error.setTimestamp(Instant.now());
		error.setPath(request.getServletPath());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		List<String> causes=new ArrayList<>();
		causes.add(ex.getMessage());
		error.setCauses(causes);
		return error;
	}
	
	private ErrorFormat toError(RuntimeException ex, HttpServletRequest request) {
		ErrorFormat error = new ErrorFormat();
		error.setTimestamp(Instant.now());
		error.setPath(request.getServletPath());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		List<String> causes=new ArrayList<>();
		causes.add(ex.getMessage());
		error.setCauses(causes);
		return error;
	}
	
	private ErrorFormat toError(HandlerMethodValidationException ex, HttpServletRequest request) {
		ErrorFormat error = new ErrorFormat();
		error.setTimestamp(Instant.now());
		error.setPath(request.getServletPath());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		List<String> causes=new ArrayList<>();
		List<? extends MessageSourceResolvable> errors= ex.getAllErrors();
		for (MessageSourceResolvable objectError : errors) {
			causes.add(objectError.getDefaultMessage());
		}
		error.setCauses(causes);
		return error;
	}
	

	@Data
	@NoArgsConstructor
	class ErrorFormat {
		
		Instant timestamp;
		
		Integer status;
		
		String error;
		
		List<String> causes;
		
		String path;
	}
	
}
