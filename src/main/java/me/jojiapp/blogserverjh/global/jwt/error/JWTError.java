package me.jojiapp.blogserverjh.global.jwt.error;

import lombok.*;

/**
 * JWT Error 관리 Enum
 */
@Getter
@RequiredArgsConstructor
public enum JWTError {

	/**
	 * 토큰 자체가 이상이 있을 경우
	 */
	BAD_TOKEN("잘못된 토큰 입니다."),

	/**
	 * 토큰이 만료된 경우
	 */
	EXPIRED("토큰이 만료되었습니다.");

	/**
	 * Error Message
	 */
	private final String message;

}
