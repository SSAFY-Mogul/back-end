package com.mogul.demo.user.auth.handler;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.dto.UserLoginResponse;
import com.mogul.demo.user.dto.UserPrincipal;
import com.mogul.demo.user.role.UserRole;
import com.mogul.demo.util.CustomResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationHandler {
	private static SuccessHandler successHandler = new SuccessHandler();
	private static FailureHandler failureHandler = new FailureHandler();

	public static AuthenticationSuccessHandler successHandler() {
		return successHandler;
	}

	public static FailureHandler failureHandler() {
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
			UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
			String userId = userPrincipal.getPassword();
			String role = userPrincipal.getAuthority().getAuthority();

			//response
			String token = tokenProvider.createToken(userId, UserRole.valueOf(role));
			AuthToken authToken = new AuthToken(token);

			response.setHeader("Authentication", authToken.getToken());
			response.setContentType("application/json");
			// response.sendRedirect("token=" + authToken.getToken());
			response.setStatus(HttpStatus.OK.value());

			CustomResponse<UserLoginResponse> responseBody = new CustomResponse<>(
				HttpStatus.OK.value(),
				new UserLoginResponse(authToken.getToken()),
				"인증 토큰이 발행되었습니다."
			);

			response.getOutputStream().write(new Gson().toJson(responseBody).getBytes());
		}
	}

	static class FailureHandler implements AuthenticationFailureHandler {
		@Override
		public void onAuthenticationFailure(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException exception
		) throws IOException {
			CustomResponse<UserLoginResponse> responseBody = new CustomResponse<>(
				HttpStatus.UNAUTHORIZED.value(),
				new UserLoginResponse(null),
				"인증되지 않은 사용자입니다."
			);

			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");

			OutputStream outputStream = response.getOutputStream();
			outputStream.write(new Gson().toJson(responseBody).getBytes());
		}
	}
}
