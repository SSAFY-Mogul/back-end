package com.mogul.demo.user.auth.entrypoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mogul.demo.util.CustomResponse;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException
	) throws IOException {
		HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;

		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(unauthorized.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		ResponseEntity<CustomResponse<Void>> responseBody = new ResponseEntity<>(
			new CustomResponse<>(
				unauthorized.value(),
				null,
				"인증에 실패했습니다."
			),
			unauthorized
		);

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(new Gson().toJson(responseBody).getBytes(StandardCharsets.UTF_8));

	}
}
