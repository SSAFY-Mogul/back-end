package com.mogul.demo.user.auth.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.google.gson.Gson;
import com.mogul.demo.util.CustomResponse;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(
		HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException
	) throws IOException {
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		ResponseEntity<CustomResponse<Void>> responseBody = new ResponseEntity<>(
			new CustomResponse<>(
				HttpStatus.FORBIDDEN.value(),
				null,
				"권한이 부족합니다."
			),
			HttpStatus.FORBIDDEN
		);

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(new Gson().toJson(responseBody).getBytes(StandardCharsets.UTF_8));
	}
}
