package com.example.EWDJ_End;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
