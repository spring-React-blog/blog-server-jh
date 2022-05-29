package me.jojiapp.blogserverjh.global.exception;

import lombok.*;
import lombok.extern.slf4j.*;
import me.jojiapp.blogserverjh.global.response.*;
import org.springframework.http.*;
import org.springframework.web.*;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;

import static me.jojiapp.blogserverjh.global.exception.GlobalError.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	private APIResponse<Void> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("message", e);
		return APIResponse.error(METHOD_NOT_ALLOWED.getMessage());
	}

	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private APIResponse<Void> badRequestException(RuntimeException e) {
		log.error("message", e);
		return APIResponse.error(e.getMessage());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private APIResponse<Void> entityNotFoundException(EntityNotFoundException e) {
		log.error("message", e);
		return APIResponse.error(ENTITY_NOT_FOUND.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private APIResponse<Void> exception(Exception e) {
		log.error("message", e);
		return APIResponse.error(INTERNAL_SERVER_ERROR.getMessage());
	}
}
