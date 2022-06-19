package me.jojiapp.blogserverjh.global.exception.convertor;

import lombok.*;
import org.springframework.context.*;
import org.springframework.context.i18n.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BindingErrorConvertor {
	private final MessageSource messageSource;

	public String getBindingError(final FieldError fieldError) {
		return Arrays.stream(Objects.requireNonNull(fieldError.getCodes()))
				.map(code -> createBindErrorOrNull(fieldError, code))
				.filter(Objects::nonNull)
				.findFirst() // 찾은 첫 번째 요소 반환
				.orElse(getErrorMessage(fieldError, fieldError.getDefaultMessage()));
	}

	private String createBindErrorOrNull(final FieldError fieldError, final String code) {
		try {
			val errorMessage = messageSource.getMessage(
					code,
					fieldError.getArguments(),
					LocaleContextHolder.getLocale()
			);
			return getErrorMessage(fieldError, errorMessage);
		} catch (NoSuchMessageException e) {
			return null;
		}
	}

	private String getErrorMessage(final FieldError fieldError, String errorMessage) {
		return "[%s: %s]".formatted(fieldError.getField(), errorMessage);
	}
}
