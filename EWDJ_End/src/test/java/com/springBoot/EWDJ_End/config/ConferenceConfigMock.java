package com.springBoot.EWDJ_End.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.Validator;

@TestConfiguration
public class ConferenceConfigMock {
	
    @Bean @Primary
    LocalDateTime conferenceRangeStart() {
        return LocalDateTime.of(2020, 1, 1, 1, 0);
    }
    
    @Bean @Primary
    LocalDateTime conferenceRangeEnd() {
        return LocalDateTime.of(2021, 1, 1, 1, 0);
    }

    @Bean @Primary
    Validator conferenceRangeValidator() {
        Validator validator = mock(Validator.class);
        when(validator.supports(any())).thenReturn(true);

        return validator;
    }
}