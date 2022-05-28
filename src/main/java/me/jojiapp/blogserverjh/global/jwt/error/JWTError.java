package me.jojiapp.blogserverjh.global.jwt.error;

import lombok.*;

@RequiredArgsConstructor
@Getter
public enum JWTError {
	BAD_TOKEN("잘못된 토큰 입니다."),
	EXPIRED("토큰이 만료되었습니다.");

	private final String message;

}
