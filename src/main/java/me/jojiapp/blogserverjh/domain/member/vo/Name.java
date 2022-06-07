package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;

import javax.persistence.*;

/**
 * 이름 VO
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Name {

	/**
	 * 이름
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 이름 생성자 팩토리 메소드
	 *
	 * @param name 이름
	 *
	 * @return 이름 VO
	 */
	public static Name of(final String name) {
		return new Name(name);
	}

}
