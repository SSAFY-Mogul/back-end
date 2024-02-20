package com.mogul.demo.user.util;

import java.util.Date;

import com.mogul.demo.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {
	private static final long ACCESS_TOKEN_VALID_PERIOD = 1000L * 60 * 60 * 24 * 8;

	public static String createToken(User user,String key){
		Claims claims = Jwts.claims();
		claims.put("email",user.getUsername());

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis()+ACCESS_TOKEN_VALID_PERIOD))
			.signWith(SignatureAlgorithm.HS256,key)
			.compact();
	}
}
