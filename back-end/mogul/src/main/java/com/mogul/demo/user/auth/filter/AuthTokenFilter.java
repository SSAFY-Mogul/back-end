package com.mogul.demo.user.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
	private final AuthTokenProvider authTokenProvider;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String requestedPath = request.getServletPath();
		String method = request.getMethod();

		//만약 path가 허용된 경로 중 하나라면 그냥 doFilter
		if(!match(method, requestedPath)) {
			filterChain.doFilter(request, response);
			return;
		}


		String token = request.getHeader("Authentication");
		// log.debug("token data : {}", token);
		AuthToken authToken = authTokenProvider.stringToToken(token);

		if (authToken != null) {
			// log.debug("token validate");
			Authentication authentication = authTokenProvider.getAuthentication(authToken);
			System.out.println(authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	public boolean match(String method, String requestedPath) {
		String[][] denyPatterns = new String[][] {
			new String[] {"GET", "/api/webtoon/{webtoon-id}/like"},
			new String[] {"POST", "/api/webtoon/{webtoon-id}/like"},
			new String[] {"DELETE", "/api/webtoon/{webtoon-id}/like"},
			new String[] {"UPDATE", "/api/webtoon/{webtoon-id}/like"},
			new String[] {"POST", "/api/review/"}
		};

		AntPathMatcher antPathMatcher = new AntPathMatcher();

		for (String[] denyPattern : denyPatterns) {
			if (method.equals(denyPattern[0]) && antPathMatcher.match(denyPattern[1], requestedPath)) {
				return false;
			}
		}

		String[] permitPatterns = new String[] {
			"/api/**",
			"/swagger-ui/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/swagger-resources/**", "/configuration/**"
		};
		for (String permitPattern : permitPatterns) {
			if(!antPathMatcher.match(permitPattern, requestedPath)) {
				return false;
			}
		}

		return true;
	}
}
