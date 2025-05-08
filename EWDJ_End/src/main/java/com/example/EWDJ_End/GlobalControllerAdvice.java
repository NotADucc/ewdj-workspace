package com.example.EWDJ_End;

import org.springframework.web.bind.annotation.ModelAttribute;

import domain.user.UserRole;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute("username")
	public String populateUsername(Authentication authentication) {
		return authentication == null ? "" : authentication.getName();
	}

	@ModelAttribute("role")
	public String populateRole(Authentication authentication) {
		if (authentication == null)
			return "";
		
		String role = authentication.getAuthorities().iterator().next().getAuthority();
		
		return UserRole.valueOf(role).resourceBundleCode();
	}
}