package com.mogul.demo.user.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final UserDetailsService userDetailsService;
	@Value("${jwt.secret}")
	private String SecretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = extractToken(request);

		if(token != null){
			if(validateToken(token)){
				Claims claims = extractClaims(token);
				String email = (String)claims.get("email");
				request.setAttribute("email",email);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}else{
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}
		}
		filterChain.doFilter(request,response);
	}

	private String extractToken(HttpServletRequest request){
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken != null && bearerToken.startsWith("Bearer")) return bearerToken.substring(7);
		return null;
	}

	private boolean validateToken(String token){
		try{
			Jwts.parser().setSigningKey(SecretKey).parseClaimsJws(token);
		}
		catch (Exception e){
			throw new IllegalArgumentException("유효하지 않은 토큰입니다");
		}
		return true;
	}

	private Claims extractClaims(String token){
		return Jwts.parser().setSigningKey(SecretKey).parseClaimsJws(token).getBody();
	}
}
