package me.jojiapp.blogserverjh.global.exception;

import lombok.*;

/**
 * Global Exception Error 관리 Enum
 */
@Getter
@RequiredArgsConstructor
public enum GlobalError {

	/**
	 * 지원하지 않는 메소드의 경우
	 */
	METHOD_NOT_ALLOWED("지원하지 않는 요청 메소드 입니다."),

	/**
	 * 서버에서 예외처리를 하지 못한 경우
	 */
	INTERNAL_SERVER_ERROR("서비스를 이용할 수 없습니다."),

	/**
	 * 엔티티가 존재하지 안흔 경우
	 */
	ENTITY_NOT_FOUND("존재하지 않는 값입니다.");


	private final String message;


}
