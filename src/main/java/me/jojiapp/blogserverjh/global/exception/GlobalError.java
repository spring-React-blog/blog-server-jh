package me.jojiapp.blogserverjh.global.exception;

import lombok.*;

/**
 * Global Exception Error 관리 Enum
 */
@Getter
@RequiredArgsConstructor
public enum GlobalError {

	METHOD_NOT_ALLOWED("지원하지 않는 요청 메소드 입니다."),
	INTERNAL_SERVER_ERROR("서비스를 이용할 수 없습니다."),
	ENTITY_NOT_FOUND("존재하지 않는 값입니다.");


	private final String message;


}
