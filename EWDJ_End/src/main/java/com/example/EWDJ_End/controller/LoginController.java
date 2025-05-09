package com.example.EWDJ_End.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String login(
			@RequestParam(required = false) String error,
			@RequestParam(required = false) String logout,
			HttpServletRequest request,
			Model model
	) {

		if (error != null) {
			var session = request.getSession(false);
			String errorMessage = messageSource.getMessage("unknownerror", null, LocaleContextHolder.getLocale());
			if (session != null) {
				Exception ex = (Exception) session
						.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
				if (ex != null) {
					errorMessage = ex.getMessage();
				}
			}
			model.addAttribute("error", errorMessage);
		}
		if (logout != null) {
			//
		}
		return "login";
	}

}