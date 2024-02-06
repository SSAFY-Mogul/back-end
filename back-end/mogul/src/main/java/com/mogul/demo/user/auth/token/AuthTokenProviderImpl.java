package com.mogul.demo.user.auth.token;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.mogul.demo.user.dto.UserAuth;
import com.mogul.demo.user.dto.UserPrincipal;
import com.mogul.demo.user.role.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@PropertySource("classpath:application.yml")
public class AuthTokenProviderImpl implements AuthTokenProvider {
	private final SecretKey key;

	private final long accessTokenLifetimeSeconds;

	public AuthTokenProviderImpl(String secret, long accessTokenLifetime) {
		this.key = new SecretKeySpec(
			secret.getBytes(StandardCharsets.UTF_8),
			"HmacSha256"
		);

		this.accessTokenLifetimeSeconds = accessTokenLifetime;
	}

	@Override
	public String createToken(String userId, Role role) {
		Date currentDate = new Date();
		Date expiration = new Date(currentDate.getTime() + (accessTokenLifetimeSeconds * 1000L));

		return Jwts.builder()
			.header()
			.add("typ", "JWT")
			.and()
			.claim("userId", userId)
			.claim("role", role)
			.issuedAt(currentDate)
			.expiration(expiration)
			.signWith(key, Jwts.SIG.HS256)
			.encodePayload(true)
			.compact();
	}

	@Override
	public boolean validate(AuthToken token) throws ExpiredJwtException {
		//token의 claim을 얻는 과정에서 예외 발생 ≡ token이 유효하지 않다
		Claims claims = token.getClaims(this.key);

		return claims != null;
	}

	@Override
	public Long getUserIdFromAuthToken(AuthToken token) {
		Claims claims = token.getClaims(key);

		return Long.parseLong((String)claims.get("userId"));
	}

	@Override
	public Duration getRemainingTime(AuthToken token) {
		Claims claims = token.getClaims(key);

		//현 시점부터 발급 당시 정해진 만기까지 남은 시간을 반환한다.
		Instant iat = new Date().toInstant();
		Instant exp = claims.getExpiration().toInstant();

		return Duration.between(iat, exp);
	}

	@Override
	public Authentication getAuthentication(AuthToken token) {
		if (validate(token)) {
			Claims claims = token.getClaims(key);

			String role = (String)claims.get("role");
			Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
				new SimpleGrantedAuthority(role));
			UserAuth userAuth = new UserAuth(
				Long.parseLong((String)claims.get("userId")),
				Role.valueOf(role)
			);

			//UsernamePasswordAuthenticationToken implements Authentication
			return new UsernamePasswordAuthenticationToken(
				UserPrincipal.create(userAuth),
				token,
				authorities
			);
		} else {
			throw new JwtException("Invalid token: " + tokenToString(token));
		}
	}

	@Override
	public String tokenToString(AuthToken token) {
		return token.getToken();
	}

	@Override
	public String resolveToken(AuthToken token) {
		String tokenString = tokenToString(token);

		String[] chunks = tokenString.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String header = new String(decoder.decode(chunks[0]));
		String payload = new String(decoder.decode(chunks[1]));

		return (header + payload);
	}

}
