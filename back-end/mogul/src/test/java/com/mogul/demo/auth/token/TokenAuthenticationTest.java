package com.mogul.demo.auth.token;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.auth.token.AuthTokenProviderImpl;
import com.mogul.demo.user.role.UserRole;

public class TokenAuthenticationTest {
	@Test
	void 토큰_인증_정보_확인() {
		AuthTokenProvider tokenProvider = new AuthTokenProviderImpl();
		AuthToken token = new AuthToken(tokenProvider.createToken("1", UserRole.USER));

		Authentication auth = tokenProvider.getAuthentication(token);
		System.out.println(auth);
	}
}
