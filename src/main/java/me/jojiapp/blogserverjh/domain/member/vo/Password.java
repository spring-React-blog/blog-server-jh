package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;

/**
 * 비밀번호 VO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Password {

	/**
	 * 비밀번호
	 */
	private String password;

	/**
	 * 비미번호 생성자 팩토리 메소드
	 *
	 * @param password 비밀번호
	 *
	 * @return 비밀번호 VO
	 */
	public static Password of(final String password) {
		return new Password(password);
	}

}
