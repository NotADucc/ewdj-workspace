package com.springBoot_bank_start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import service.BankCustomerService;
import service.BankCustomerServiceImpl;

@SpringBootApplication
public class SpringBootBankStartApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBankStartApplication.class, args);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/bank");
	}
	@Bean
	BankCustomerService colorBeanService() {
		return new BankCustomerServiceImpl();
	}
}
