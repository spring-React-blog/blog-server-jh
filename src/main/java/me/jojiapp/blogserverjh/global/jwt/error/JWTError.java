package me.jojiapp.blogserverjh.global.jwt.error;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum JWTError {

	BAD_TOKEN("잘못된 토큰 입니다."),
	EXPIRED("토큰이 만료되었습니다."),
	ACCESS_TOKEN_NOT_EXPIRED("Access Token이 만료되지 않았습니다."),
	NOT_ACCESS_TOKEN("Access Token이 아닙니다."),
	NOT_REFRESH_TOKEN("Refrsh Token이 아닙니다.");
	private final String message;

}
