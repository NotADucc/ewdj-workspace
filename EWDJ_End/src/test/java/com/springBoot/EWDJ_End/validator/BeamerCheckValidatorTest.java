package com.springBoot.EWDJ_End.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.springBoot.EWDJ_End.util.BeamerClass;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

class BeamerCheckValidatorTest {

	private Validator beamerChecksumValidator;

	@BeforeEach
	public void beforeEach() {
		var factory = Validation.buildDefaultValidatorFactory();
		beamerChecksumValidator = factory.getValidator();
	}

	private static Stream<Arguments> validChecksums() {
		return Stream.of(
				Arguments.of("0097", 0),
				Arguments.of("0194", 0),
				Arguments.of("0291", 0),
				Arguments.of("9700", 0)
		);
	}

	@ParameterizedTest
	@MethodSource("validChecksums")
	public void testValidChecksum(String code, int checksum) {
		var beamerClass = new BeamerClass(code, checksum);
		var errors = beamerChecksumValidator.validate(beamerClass);
		assertThat(errors).isEmpty();
	}

	private static Stream<Arguments> invalidChecksums() {
		return Stream.of(
				Arguments.of("0097", 1),
				Arguments.of("0194", 1),
				Arguments.of("0291", 1),
				Arguments.of("9700", 1)
		);
	}

	@ParameterizedTest
	@MethodSource("invalidChecksums")
	public void testInvalidChecksum(String code, int checksum) {
		var beamerClass = new BeamerClass(code, checksum);
		var errors = beamerChecksumValidator.validate(beamerClass);
		assertThat(errors).isNotEmpty();
		assertThat(errors.iterator().next().getPropertyPath().toString()).isEqualTo("beamercheck");
	}
}
