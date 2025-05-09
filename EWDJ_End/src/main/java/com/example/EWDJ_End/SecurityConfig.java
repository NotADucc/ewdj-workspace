package com.example.EWDJ_End;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository()))
				.authorizeHttpRequests(
						requests -> requests
								.requestMatchers(
										"/login**",
										"/css/**",
										"/img/**",
										"/error/**",
										"/fragments/**",
										"/",
										"/changeLocale",
										"/events"
								).permitAll().requestMatchers("/events/**")
								.hasAnyRole("USER", "ADMIN")
				)
				.formLogin(
						form -> form.defaultSuccessUrl("/events", true).loginPage("/login")
								.usernameParameter("username").passwordParameter("password")
				).exceptionHandling(handling -> handling.accessDeniedPage("/error/access-denied"));

		return http.build();
	}
}