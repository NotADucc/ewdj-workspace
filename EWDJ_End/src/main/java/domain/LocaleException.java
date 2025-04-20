package domain;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class LocaleException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    private static MessageSource messageSource;

    private final String code;
    private final Object[] object;

    public LocaleException(String code) {
        this.code = code;
        this.object = null;
    }

    public LocaleException(String code, Object[] object) {
        this.code = code;
        this.object = object;
    }

    @Override
    public String getMessage() {
        if (messageSource == null) return code;
        return messageSource.getMessage(code, object, LocaleContextHolder.getLocale());
    }

    public static void setMessageSource(MessageSource source) {
        messageSource = source;
    }
}
