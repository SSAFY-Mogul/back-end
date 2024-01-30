package com.mogul.demo.user.auth.token;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${jwt.duration}")
	String duration;

	public AuthTokenProviderImpl(String secret) {
		this.key = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS256.getId());
	}

	@Override
	public String createToken(String userId, UserRole role) {
		Date currentDate = new Date();
		Date expiration = new Date(currentDate.getTime() + (Long.parseLong(duration) * 1000L));

		return Jwts.builder()
			.header()
				.add("typ", "JWT")
				.and()
			.id(userId)
			.claim("role", role)  //ROLE_ADMIN, ROLE_USER
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
		String userId = claims.getId();
		UserRole role = UserRole.valueOf((String)claims.get("role"));

		return UserPrincipal.create(new UserAuth(userId, UserRole.USER));
	}

	@Override
	public Authentication getAuthentication(AuthToken authToken) {
		if (validate(authToken)) {
			Claims claims = authToken.getClaims(key);

			String role = (String)claims.get("role");
			Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
				new SimpleGrantedAuthority(role));
			UserAuth userAuth = new UserAuth((String)claims.get("id"), UserRole.valueOf(role));

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
			throw new JwtException("Invalid token: " + tokenString);
		}

		return token;
	}

	@Override
	public String tokenToString(AuthToken token) {
		return token.getToken();
	}
}
