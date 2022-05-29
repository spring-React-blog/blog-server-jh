package me.jojiapp.blogserverjh.global.exception;

import lombok.*;
import lombok.extern.slf4j.*;
import me.jojiapp.blogserverjh.global.response.*;
import org.springframework.http.*;
import org.springframework.web.*;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;

import static me.jojiapp.blogserverjh.global.exception.GlobalError.*;

/**
 * Global Exception Handler
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	/**
	 * 지원하지 않는 메소드를 호출했을 경우 발생하는 예외 처리
	 *
	 * @param e 예외
	 *
	 * @return 예외 메세지
	 *
	 * @see APIResponse
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	private APIResponse<Void> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("message", e);
		return APIResponse.error(METHOD_NOT_ALLOWED.getMessage());
	}

	/**
	 * 잘못된 요청으로 예외가 발생하는 경우 예외 처리
	 *
	 * @param e 예외
	 *
	 * @return 예외 메세지
	 *
	 * @see APIResponse
	 */
	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private APIResponse<Void> badRequestException(RuntimeException e) {
		log.error("message", e);
		return APIResponse.error(e.getMessage());
	}

	/**
	 * 엔티티가 존재하지 않는 경우 발생하는 예외 처리
	 *
	 * @param e 예외
	 *
	 * @return 예외 메세지
	 *
	 * @see APIResponse
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private APIResponse<Void> entityNotFoundException(EntityNotFoundException e) {
		log.error("message", e);
		return APIResponse.error(ENTITY_NOT_FOUND.getMessage());
	}

	/**
	 * 서버에서 처리하지 못한 예외가 발생하는 경우 예외 처리
	 *
	 * @param e 예외
	 *
	 * @return 예외 메세지
	 *
	 * @see APIResponse
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private APIResponse<Void> exception(Exception e) {
		log.error("message", e);
		return APIResponse.error(INTERNAL_SERVER_ERROR.getMessage());
	}
}
