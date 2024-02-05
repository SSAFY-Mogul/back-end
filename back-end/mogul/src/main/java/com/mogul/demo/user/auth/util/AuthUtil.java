package com.mogul.demo.user.auth.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.mogul.demo.user.auth.exception.UnauthorizedException;
import com.mogul.demo.user.dto.UserPrincipal;

public class AuthUtil {
	public static UserPrincipal getAuthenticationInfo() {
		return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static long getAuthenticationInfoId() throws UnauthorizedException {
		try {
			return Long.parseLong(getAuthenticationInfo().getUsername());
		} catch (NumberFormatException | NullPointerException e) {
			throw new UnauthorizedException();
		}
	}
}
