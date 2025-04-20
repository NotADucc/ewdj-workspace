package com.example.EWDJ_End;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import domain.LocaleException;

@Component
public class MessageSourceInjector {
    public MessageSourceInjector(MessageSource messageSource) {
        LocaleException.setMessageSource(messageSource);
    }
}
