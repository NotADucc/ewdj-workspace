package com.springBoot.EWDJ_End.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.springBoot.EWDJ_End.util.EmptySpeakersClass;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

class EmptySpeakerValidatorTest {

	private Validator emptySpeakerValidator;

	@BeforeEach
	public void beforeEach() {
		var factory = Validation.buildDefaultValidatorFactory();
		emptySpeakerValidator = factory.getValidator();
	}

	@Test
	public void testValidNoDuplicates() {
		var speakersClass = new EmptySpeakersClass(List.of("Gwil", "Robert", "Teddy"));
		var errors = emptySpeakerValidator.validate(speakersClass);
		assertThat(errors).isEmpty();
	}

	private static Stream<Arguments> invalidSpeakers() {
		return Stream.of(
				Arguments.of(List.of("Gwil", "")),
				Arguments.of(List.of("", "gwil")),
				Arguments.of(List.of("", "")),
				Arguments.of(List.of("", "", "")),
				Arguments.of(List.of(""))
		);
	}

	@ParameterizedTest
	@MethodSource("invalidSpeakers")
	public void testInvalidDuplicates(List<String> speakers) {
		var speakersClass = new EmptySpeakersClass(speakers);
		var errors = emptySpeakerValidator.validate(speakersClass);
		assertThat(errors).isNotEmpty();
		assertThat(errors.iterator().next().getPropertyPath().toString()).isEqualTo("speakers");
	}
}
