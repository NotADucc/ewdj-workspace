package com.springBoot.EWDJ_End.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.springBoot.EWDJ_End.util.SpeakersClass;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

class DuplicateSpeakerValidatorTest {

	private Validator duplicateSpeakerValidator;

	@BeforeEach
	public void beforeEach() {
		var factory = Validation.buildDefaultValidatorFactory();
		duplicateSpeakerValidator = factory.getValidator();
	}

	@Test
	public void testValidNoDuplicates() {
		var speakersClass = new SpeakersClass(List.of("Gwil", "Robert", "Teddy"));
		var errors = duplicateSpeakerValidator.validate(speakersClass);
		assertThat(errors).isEmpty();
	}

	private static Stream<Arguments> invalidSpeakers() {
		return Stream.of(
				Arguments.of(List.of("Gwil", "Gwil")),
				Arguments.of(List.of("Gwil", "gwil")),
				Arguments.of(List.of("gwil", "Gwil")),
				Arguments.of(List.of("gwil", "Robert", "Gwil"))
		);
	}

	@ParameterizedTest
	@MethodSource("invalidSpeakers")
	public void testInvalidDuplicates(List<String> speakers) {
		var speakersClass = new SpeakersClass(speakers);
		var errors = duplicateSpeakerValidator.validate(speakersClass);
		assertThat(errors).isNotEmpty();
		assertThat(errors.iterator().next().getPropertyPath().toString()).isEqualTo("speakers");
	}
}
