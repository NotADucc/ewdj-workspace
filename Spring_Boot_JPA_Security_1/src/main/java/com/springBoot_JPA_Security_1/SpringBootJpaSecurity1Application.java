package com.springBoot_JPA_Security_1;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import service.MyUserDetailsService;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class SpringBootJpaSecurity1Application implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaSecurity1Application.class, args);
	}

	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
	   registry.addRedirectViewController("/", "/welcome");
	   registry.addViewController("/403").setViewName("403");
    }

	@Bean
	UserDetailsService myUserDetailsService() {
		return new MyUserDetailsService();
	}

	@Bean
	LocaleResolver localeResolver() {
	    CookieLocaleResolver slr = new CookieLocaleResolver();
	    slr.setDefaultLocale(Locale.ENGLISH);
	    return slr;
	}

}
