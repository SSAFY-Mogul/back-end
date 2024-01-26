package com.mogul.demo.user.auth.token;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// @Slf4j
public class AuthToken {
	private AuthTokenProvider authTokenProvider;

	@Value("${jwt.secret}")
	private String secret;
	private static SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

	private String userId;
	private Date expiry;

	private final String token;
	private final Key key;

	AuthToken(String userId, Date expiry) {
		this.key = createKey();
		authTokenProvider = new AuthTokenProviderImpl();
		token = authTokenProvider.createToken(userId, expiry);

		this.userId = userId;
		this.expiry = expiry;
	}
	private Key createKey() {
		return new SecretKeySpec(secret.getBytes(), algorithmToString());
	}

	private static String algorithmToString() {
		return algorithm.getJcaName();
	}

	@Override
	public String toString() {
		return token;
	}


	public String createToken(String userId, Date expiry) {
		return Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setSubject("token")
			.claim("aud", userId) //토큰 대상은 user_id로 식별
			.setIssuedAt(new Date()) //현재 날짜
			.setExpiration(expiry)
			.signWith(key, algorithm)
			.compact();

	}

	public boolean validate() {
		return this.getClaims() != null;
	}

	public Claims getClaims() {
		Claims claims = null;
		try {
			claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (
			ExpiredJwtException |
			UnsupportedJwtException |
			MalformedJwtException |
			IllegalArgumentException ignored) {
			//log.debug("Auth Token is not validated. : {}", token);
		}
		return claims;
	}


}