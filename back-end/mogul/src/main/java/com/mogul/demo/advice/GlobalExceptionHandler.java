package com.mogul.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.util.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
		return ResponseEntity.ok(new ErrorResponse(HttpStatus.NOT_EXTENDED.value(), ex.getMessage()));
	}

	// 다른 예외 처리 메서드 추가 가능
}

