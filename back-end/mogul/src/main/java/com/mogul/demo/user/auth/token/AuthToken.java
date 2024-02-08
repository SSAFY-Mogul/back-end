package com.mogul.demo.user.auth.token;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthToken {
	@Getter(AccessLevel.PACKAGE)
	private final String token;

	public Claims getClaims(SecretKey key) throws ExpiredJwtException {
		Claims claims = null;
		try {
			claims = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		} catch (ExpiredJwtException e) {
			throw e;
		} catch (JwtException | IllegalArgumentException ignored) {
			// log.debug("Auth Token is Not Validated. : {}", token)
		}

		return claims;
	}
}