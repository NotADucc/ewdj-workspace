package com.example.EWDJ_End;

import org.springframework.web.bind.annotation.ModelAttribute;

import domain.user.UserRole;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalControllerAdvice {
	@ModelAttribute("isLoggedIn")
	public boolean populateLoggedIn(Authentication authentication) {
		if (authentication == null)
			return false;

		return authentication.isAuthenticated();
	}
	
	@ModelAttribute("isUSER")
	public boolean populateisUSER(Authentication authentication) {
		if (authentication == null)
			return false;

		return parseRole(authentication) == UserRole.USER;
	}
	
	@ModelAttribute("isADMIN")
	public boolean populateisADMIN(Authentication authentication) {
		if (authentication == null)
			return false;

		return parseRole(authentication) == UserRole.ADMIN;
	}

	@ModelAttribute("username")
	public String populateUsername(Authentication authentication) {
		return authentication == null ? "" : authentication.getName();
	}

	@ModelAttribute("role")
	public String populateRole(Authentication authentication) {
		if (authentication == null)
			return "";

		return parseRole(authentication).resourceBundleCode();
	}
	
	private static final UserRole parseRole(Authentication authentication) {
		if (authentication == null)
			return UserRole.BANNED;
		
		String role = authentication.getAuthorities().iterator().next().getAuthority()
				.replace("ROLE_", "");
		
		return UserRole.valueOf(role);
	}
}