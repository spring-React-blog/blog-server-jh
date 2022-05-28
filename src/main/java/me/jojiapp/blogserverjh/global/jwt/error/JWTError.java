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
	EXPIRED("토큰이 만료되었습니다."),

	/**
	 * Access Token이 만료되지 않은 경우
	 */
	ACCESS_TOKEN_NOT_EXPIRED("Access Token이 만료되지 않았습니다."),

	/**
	 * Access Token이 아닌 경우
	 */
	NOT_ACCESS_TOKEN("Access Token이 아닙니다."),

	/**
	 * Refrsh Token이 아닌 경우
	 */
	NOT_REFRESH_TOKEN("Refrsh Token이 아닙니다.");

	/**
	 * Error Message
	 */
	private final String message;

}
