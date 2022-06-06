package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;

/**
 * 이메일 VO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Email {

	/**
	 * 이메일
	 */
	private String email;

	/**
	 * 이메일 생성자 팩토리 메소드
	 *
	 * @param email 이메일
	 *
	 * @return 이메일 VO
	 */
	public static Email of(final String email) {
		return new Email(email);
	}

}
