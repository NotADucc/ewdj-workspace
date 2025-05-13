package com.springBoot.EWDJ_End.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springBoot.EWDJ_End.formatter.DateFormatter;
import com.springBoot.EWDJ_End.formatter.DatetimeFormatter;

@Configuration
public class FormatterConfig {
	@Bean
	DateFormatter dateFormatter() {
		return new DateFormatter();
	}
	
	@Bean
	DatetimeFormatter datetimeFormatter() {
		return new DatetimeFormatter();
	}
}
