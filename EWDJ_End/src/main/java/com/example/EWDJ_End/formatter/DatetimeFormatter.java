package com.example.EWDJ_End.formatter;

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
	public LocalDateTime parse(String text, Locale locale) {
		try {
			return LocalDateTime.parse(text, formatter(locale));
		} catch (Exception ex) {
			return LocalDateTime.parse(text, formatterBackup(locale));
		}
	}

	private DateTimeFormatter formatter(Locale locale) {
		return DateTimeFormatter.ofPattern(
				messageSource.getMessage("datetime.format.pattern", null, locale),
				locale
		);
	}
	
	private DateTimeFormatter formatterBackup(Locale locale) {
		return DateTimeFormatter.ofPattern(
				messageSource.getMessage("datetime.format.pattern.backup", null, locale),
				locale
		);
	}
}
