package com.springBoot.EWDJ_End.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import domain.event.Event;
import init.InitEvent;
import validator.ConferenceRangeValidator;

class ConferencRangeValidatorTest {

	private Validator conferenceRangeValidator;

	@BeforeEach
	public void beforeEach() {
		LocalDateTime start = LocalDateTime.of(2020, 1, 1, 1, 0);
		LocalDateTime end = start.plusYears(1);
		conferenceRangeValidator = new ConferenceRangeValidator(start, end);
	}

	private Event getEvent(LocalDateTime time) {
		return new Event(
				InitEvent.OK_ID,
				InitEvent.OK_NAME,
				InitEvent.OK_DESCRIPTION,
				InitEvent.OK_ROOM,
				time,
				InitEvent.OK_B_CODE,
				InitEvent.OK_B_CHECK,
				InitEvent.OK_PRICE,
				InitEvent.OK_SPEAKERS
		);
	}

	private static Stream<Arguments> validDateTimes() {
		return Stream.of(
				Arguments.of(LocalDateTime.of(2020, 1, 1, 1, 0)),
				Arguments.of(LocalDateTime.of(2021, 1, 1, 1, 0)),
				Arguments.of(LocalDateTime.of(2020, 2, 1, 1, 0)),
				Arguments.of(LocalDateTime.of(2020, 12, 1, 1, 0))
		);
	}

	@ParameterizedTest
	@MethodSource("validDateTimes")
	public void testValidNumbers(LocalDateTime dateTime) {
		var event = getEvent(dateTime);
		var errors = new BeanPropertyBindingResult(event, "account");
		conferenceRangeValidator.validate(event, errors);
		assertThat(errors.getAllErrors()).isEmpty();
	}

	private static Stream<Arguments> invalidDateTimes() {
		return Stream.of(
				Arguments.of(LocalDateTime.of(2019, 1, 1, 1, 0)),
				Arguments.of(LocalDateTime.of(2021, 1, 1, 2, 0)),
				Arguments.of(LocalDateTime.of(2022, 1, 1, 1, 0))
		);
	}

	@ParameterizedTest
	@MethodSource("invalidDateTimes")
	public void testInvalidNumberTimes(LocalDateTime dateTime) {
		var event = getEvent(dateTime);
		var errors = new BeanPropertyBindingResult(event, "account");
		conferenceRangeValidator.validate(event, errors);
		assertThat(errors.getAllErrors()).isNotEmpty();
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("dateTime")).isNotNull();
	}
}
