package com.springBoot.beer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import domain.BeerExpertBean;
import domain.ColorBean;
import domain.ColorExpertBean;
import domain.ExpertBean;

@SpringBootApplication
public class SpringBootBeerApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBeerApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/beer");
	}

	@Bean
	ColorBean colorBeanService() {
		return new ColorExpertBean();
	}
	@Bean
	ExpertBean expertBeanService() {
		return new BeerExpertBean();
	}
}
