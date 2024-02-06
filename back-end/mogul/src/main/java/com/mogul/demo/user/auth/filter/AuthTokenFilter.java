package com.mogul.demo.user.auth.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.util.CustomResponse;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
 * @Component 등 Bean으로 등록하는 경우
 * security filter chain이 아닌 default chain filter에 등록된다.
 */
@RequiredArgsConstructor
// 인증은 한 번만 이루어져야 하므로 OncePerRequestFilter를 상속받는다.
public class AuthTokenFilter extends OncePerRequestFilter {
	private final AuthTokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		// log.debug("token data : {}", token);
		try {
			// 여기서는 validate() 내부에서 claim을 얻어냄으로써 두 가지를 검증한다.
			// 1. 우리가 발급한 토큰이 맞는가?
			// 2. 만료된 토큰인가?
			AuthToken authToken = new AuthToken(token);
			if (tokenProvider.validate(authToken)) {
				Authentication authentication = tokenProvider.getAuthentication(authToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException je) {
			HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;

			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setStatus(unauthorized.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			ResponseEntity<CustomResponse<String>> responseBody = new ResponseEntity<>(
				new CustomResponse<>(
					unauthorized.value(),
					"",
					"인증 정보가 유효하지 않습니다."
				),
				unauthorized
			);

			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(new Gson().toJson(responseBody).getBytes(StandardCharsets.UTF_8));
		}

	}

}
