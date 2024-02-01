package com.mogul.demo.user.auth.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.dto.UserPrincipal;
import com.mogul.demo.user.role.UserRole;
import com.mogul.demo.util.CustomResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@NoArgsConstructor
public class JwtAuthenticationHandler {
	private static final SuccessHandler successHandler = new SuccessHandler();
	private static final FailureHandler failureHandler = new FailureHandler();

	public static AuthenticationSuccessHandler successHandler() {
		return successHandler;
	}

	public static AuthenticationFailureHandler failureHandler() {
		return failureHandler;
	}

	static class SuccessHandler implements AuthenticationSuccessHandler {
		AuthTokenProvider tokenProvider;

		@Override
		public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
		) throws IOException, ServletException {
			//이 메소드가 호출됐다 ≡ 이미 SecurityContext에 해당 사용자 정보가 등록되어 있다
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			String userId = userPrincipal.getPassword();
			String role = userPrincipal.getAuthority().getAuthority();

			//token
			String token = tokenProvider.createToken(userId, UserRole.valueOf(role));
			AuthToken authToken = new AuthToken(token);

			//response
			HttpStatus ok = HttpStatus.OK;

			response.setHeader("Authorization", authToken.getToken());
			response.setStatus(ok.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			ResponseEntity<CustomResponse<Void>> responseBody = ResponseEntity.ok(
				new CustomResponse<>(
					ok.value(),
					null,
					"인증 토큰이 발행되었습니다."
				)
			);

			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(new Gson().toJson(responseBody).getBytes(StandardCharsets.UTF_8));
		}
	}

	static class FailureHandler implements AuthenticationFailureHandler {
		@Override
		public void onAuthenticationFailure(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException exception
		) throws IOException {
			HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;

			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setStatus(unauthorized.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			ResponseEntity<CustomResponse<Void>> responseBody = new ResponseEntity<>(
				new CustomResponse<>(
					unauthorized.value(),
					null,
					"인증되지 않은 사용자입니다."
				),
				unauthorized
			);

			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(new Gson().toJson(responseBody).getBytes(StandardCharsets.UTF_8));
		}
	}
}
