package com.springBoot.jpaDocentCampus_start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import service.SchoolService;
import service.SchoolServiceImpl;

@SpringBootApplication
//TODO
public class SpringBootJpaDocentCampusStartApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaDocentCampusStartApplication.class, args);
	}

	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
	   registry.addRedirectViewController("/", "/school");
    }
	
	@Bean
	SchoolService schoolservice() {
		return new SchoolServiceImpl();
	}
}
