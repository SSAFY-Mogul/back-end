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
import com.mogul.demo.user.auth.exception.RevokedTokenException;
import com.mogul.demo.user.auth.service.RedisService;
import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.util.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
 * @Component 등 Bean으로 등록하는 경우
 * security filter chain이 아닌 default filter chain에 등록된다.
 */
@RequiredArgsConstructor
// 인증은 한 번만 이루어져야 하므로 OncePerRequestFilter를 상속받는다.
public class AuthTokenFilter extends OncePerRequestFilter {
	private final AuthTokenProvider tokenProvider;
	private final RedisService redisService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String token = request.getHeader("Authorization");

		try {
			// 여기서는 validate() 내부에서 claim을 얻어냄으로써 두 가지를 검증한다.
			// 1. 우리가 발급한 토큰이 맞는가?
			// 2. 만료된 토큰인가?
			AuthToken authToken = new AuthToken(token);
			if (tokenProvider.validate(authToken)) {

				// 파기된 인증 토큰인가?
				// 키의 존재 여부만 보면 안되고 값까지 비교해야 한다.
				Long userId = tokenProvider.getUserIdFromAuthToken(authToken);
				if(redisService.existsBykey(userId)) {
					if(token.equals(redisService.findBykey(userId))) {
						throw new RevokedTokenException();
					}
				}

				Authentication authentication = tokenProvider.getAuthentication(authToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException je) {
			HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;

			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			ResponseEntity<ErrorResponse> responseBody = new ResponseEntity<>(
				new ErrorResponse(
					unauthorized.value(),
					"인증 정보가 만료되었습니다. 다시 로그인해주세요."
				),
				unauthorized
			);

			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(new Gson().toJson(responseBody).getBytes(StandardCharsets.UTF_8));
		} catch (RevokedTokenException re) {
			HttpStatus badRequest = HttpStatus.BAD_REQUEST;

			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setStatus(badRequest.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			ResponseEntity<ErrorResponse> responseBody = new ResponseEntity<>(
				new ErrorResponse(
					badRequest.value(),
					re.getMessage()
				),
				badRequest
			);

			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(new Gson().toJson(responseBody).getBytes(StandardCharsets.UTF_8));
		}

	}

}
