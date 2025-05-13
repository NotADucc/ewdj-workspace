package com.example.EWDJ_End.formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

public class DatetimeFormatter implements Formatter<LocalDateTime> {

	@Autowired
	private MessageSource messageSource;

	public DatetimeFormatter() {
		super();
	}

	@Override
	public String print(LocalDateTime object, Locale locale) {
		return object.format(formatter(locale));
	}

	@Override
	public LocalDateTime parse(String text, Locale locale) throws ParseException {
		return LocalDateTime.parse(text, formatter(locale));
	}

	private DateTimeFormatter formatter(Locale locale) {
		return DateTimeFormatter.ofPattern(
				messageSource.getMessage("datetime.format.pattern", null, locale),
				locale
		);
	}
}
