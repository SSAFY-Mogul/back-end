package com.mogul.demo.auth.token;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.auth.token.AuthTokenProviderImpl;
import com.mogul.demo.user.role.UserRole;

import io.jsonwebtoken.Jwts;

public class JwtKeyCreationTest {
	@Test
	void 토큰_생성_테스트() {
		AuthTokenProvider tokenProvider = new AuthTokenProviderImpl();
		String token = tokenProvider.createToken("1", UserRole.USER);
		String resolved = tokenProvider.resolveToken(tokenProvider.stringToToken(token));

		Assertions.assertThat(
			resolved.matches(
				"\\{\"typ\":\"JWT\",\"alg\":\"HS256\"\\}"
				+ "\\{\"userId\":\"1\",\"role\":\"USER\",\"iat\":[0-9]+,\"exp\":[0-9]+\\}")
		).isEqualTo(true);
	}
}
