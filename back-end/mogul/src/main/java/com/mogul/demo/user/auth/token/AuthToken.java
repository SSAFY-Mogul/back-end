package com.mogul.demo.user.auth.token;


import java.security.Key;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthToken {
	AuthTokenProvider tokenProvider;

	@Getter
	private final String token;

	public Claims getClaims(SecretKey key) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		} catch (JwtException | IllegalArgumentException ignored) {
			// log.debug("Auth Token is Not Validated. : {}", token);
		}

		return claims;
	}
}