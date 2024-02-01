package com.mogul.demo.user.auth.token;

import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mogul.demo.user.dto.UserAuth;
import com.mogul.demo.user.dto.UserPrincipal;
import com.mogul.demo.user.role.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@PropertySource("classpath:application.yml")
public class AuthTokenProviderImpl implements AuthTokenProvider {
	private final SecretKey key;
	private final boolean initialized;

	private static final long DURATION = 86400; //24h


	public AuthTokenProviderImpl() {
		this.key = Jwts.SIG.HS256.key().build();
		initialized = true;
	}


	public SecretKey key() {
		return this.key;
	}

	@Override
	public String createToken(String userId, UserRole role) {
		Date currentDate = new Date();
		Date expiration = new Date(currentDate.getTime() + (DURATION * 1000L));

		return Jwts.builder()
			.header()
				.add("typ", "JWT")
				.and()
			// .id(userId) //jti 설정
			.claim("userId", userId)
			.claim("role", role)
			.issuedAt(currentDate)
			.expiration(expiration)
			.signWith(key)
			.compact();
	}

	@Override
	public boolean validate(AuthToken token) {
		//token의 claim을 얻는 과정에서 예외 발생 ≡ token이 유효하지 않다
		return token.getClaims(key) != null;
	}

	@Override
	public UserDetails getUser(AuthToken token) {
		Claims claims = token.getClaims(key);
		String userId = (String) claims.get("userId");
		UserRole role = UserRole.valueOf((String)claims.get("role"));

		return UserPrincipal.create(new UserAuth(userId, role));
	}

	@Override
	public Long getUserIdFromAuthToken(AuthToken token) {
		Claims claims = token.getClaims(key);

		return Long.parseLong((String) claims.get("userId"));
	}

	@Override
	public Authentication getAuthentication(AuthToken authToken) {
		if (validate(authToken)) {
			Claims claims = authToken.getClaims(key);

			String role = (String)claims.get("role");
			Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
				new SimpleGrantedAuthority(role));
			UserAuth userAuth = new UserAuth((String) claims.get("id"), UserRole.valueOf(role));

			//UsernamePasswordAuthenticationToken implements Authentication
			return new UsernamePasswordAuthenticationToken(UserPrincipal.create(userAuth), authToken, authorities);
		} else {
			throw new JwtException("Invalid token: " + tokenToString(authToken));
		}
	}

	@Override
	public AuthToken stringToToken(String tokenString) {
		AuthToken token = new AuthToken(tokenString);
		if (!validate(token)) {
			// throw new JwtException("Invalid token: " + tokenString);
			return null;
		}

		return token;
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
