package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;

import javax.persistence.*;

/**
 * 닉네임 VO
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Nickname {

	/**
	 * 닉네임
	 */
	@Column(nullable = false)
	private String nickname;

	/**
	 * 닉네임 생성자 팩토리 메소드
	 *
	 * @param nickname 닉네임
	 *
	 * @return 닉네임 VO
	 */
	public static Nickname of(final String nickname) {
		return new Nickname(nickname);
	}
}
